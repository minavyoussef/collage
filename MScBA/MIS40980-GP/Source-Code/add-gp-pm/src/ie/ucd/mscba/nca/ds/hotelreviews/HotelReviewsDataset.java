/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.ds.hotelreviews;

import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.FilesPathesCollection;
import ie.ucd.mscba.nca.common.OperationResult;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.ds.DatasetBase;
import ie.ucd.mscba.nca.ds.ObservationsList;

import java.io.IOException;
import java.nio.charset.Charset;

public class HotelReviewsDataset extends DatasetBase {
  public HotelReviewsDataset(String hotelReviewDatasetPath) {
    DetectorException.throwIfNullOrEmpty(hotelReviewDatasetPath, "hotelReviewDatasetPath");
    DetectorException.throwIfPathNotExists(hotelReviewDatasetPath);

    this.hotelReviewDatasetPath = hotelReviewDatasetPath; // "./corpus/op_spam_v1.4"
    trainingDataSubset = new ObservationsList();
    testingDataSubset = new ObservationsList();
  }

  @Override
  public OperationResult load(long seed, double trainingPercentage, double testingPercentage) {
    if (trainingPercentage + testingPercentage != 1.0) {
      return new OperationResult(false, String.format("Invalid training to testing dataset percentage"));
    }

    Utilites.seed(seed);

    // Load corpus.
    ObservationsList dataset = new ObservationsList();
    FilesPathesCollection filesPathes = Utilites.getFilesPathesByDirectory(hotelReviewDatasetPath);
    for (String filePath : filesPathes) {
      try {
        processObservationFile(filePath, dataset);
      } catch (IOException e) {
        e.printStackTrace();
        return new OperationResult(false, "Invalid dataset directory", e);
      }
    }

    int datasetCount = dataset.size();
    int trainingDatasetCount = (int) (trainingPercentage * datasetCount);
    int testingDatasetCount = datasetCount - trainingDatasetCount;

    while (!dataset.isEmpty()) {
      int randIdx = Utilites.randomize(0, dataset.size() - 1);
      boolean sampled = false;

      if (Utilites.randomizeBinary()) {
        if (trainingDatasetCount > 0) {
          trainingDataSubset.add(dataset.get(randIdx));
          --trainingDatasetCount;
          sampled = true;
        }
      } else {
        if (testingDatasetCount > 0) {
          testingDataSubset.add(dataset.get(randIdx));
          --testingDatasetCount;
          sampled = true;
        }
      }

      if (sampled) {
        dataset.remove(randIdx);
      }
    }

    return new OperationResult();
  }

  @Override
  public ObservationsList getTrainingSet() {
    return trainingDataSubset;
  }

  @Override
  public ObservationsList getTestingSet() {
    return testingDataSubset;
  }

  private void processObservationFile(String observationFilePath, ObservationsList dataset) throws IOException {
    HotelReviewObservation reviewObservation = new HotelReviewObservation();

    if (isValidObservation(observationFilePath)) {
      processObservationRawText(reviewObservation, observationFilePath);
      processObservationWordsVector(reviewObservation, observationFilePath);
      processObservationAttributes(reviewObservation, observationFilePath);
      processObservationDeceptionLevel(reviewObservation, observationFilePath);

      dataset.add(reviewObservation);
    }
  }

  private boolean isValidObservation(String observationFilePath) {
    String fileName = Utilites.getFileName(observationFilePath).trim();
    return fileName.matches("^(d|t)(_)(.*)(_)(\\d+).txt$");
  }

  private void processObservationRawText(HotelReviewObservation reviewObservation, String observationFilePath) throws IOException {
    String fileContent = Utilites.readFileContent(observationFilePath, Charset.defaultCharset());
    reviewObservation.setRawText(fileContent);
  }
  private void processObservationWordsVector(HotelReviewObservation reviewObservation, String observationFilePath) throws IOException {
    String fileContent = Utilites.readFileContent(observationFilePath, Charset.defaultCharset());
    reviewObservation.getStatementsWordsVector().processRawText(fileContent);
  }

  private void processObservationAttributes(HotelReviewObservation reviewObservation, String observationFilePath) {
    if (observationFilePath.contains("negative_polarity")) {
      reviewObservation.getAttributes().put(POLARITY_ATTRIBUTE, "negative");
    } else if (observationFilePath.contains("positive_polarity")) {
      reviewObservation.getAttributes().put(POLARITY_ATTRIBUTE, "positive");
    } else {
      throw new IllegalArgumentException("Unexpected path schema: " + observationFilePath);
    }
  }

  private void processObservationDeceptionLevel(HotelReviewObservation reviewObservation, String observationFilePath) {
    String fileName = Utilites.getFileName(observationFilePath);

    if (fileName.charAt(0) == 'd') {
      reviewObservation.setDeceptionLevel(DeceptionLevel.Deceptive);
    } else if (fileName.charAt(0) == 't') {
      reviewObservation.setDeceptionLevel(DeceptionLevel.Truthful);
    } else
      throw new IllegalArgumentException("Unexpected filename schema: " + observationFilePath);
  }

  private static String POLARITY_ATTRIBUTE = "polarity";

  private String hotelReviewDatasetPath;
  private ObservationsList trainingDataSubset;
  private ObservationsList testingDataSubset;
}
