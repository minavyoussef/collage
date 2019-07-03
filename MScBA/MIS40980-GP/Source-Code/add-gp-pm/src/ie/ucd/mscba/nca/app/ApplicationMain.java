/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.app;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTable;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.OperationResult;
import ie.ucd.mscba.nca.ds.ObservationsList;
import ie.ucd.mscba.nca.ds.hotelreviews.HotelReviewsDataset;
import ie.ucd.mscba.nca.experiment.ExperimentExecuter;
import ie.ucd.mscba.nca.gp.Context;
import ie.ucd.mscba.nca.gp.Executer;
import ie.ucd.mscba.nca.tm.cues.deception.DeceptionFeatureExtractor;

import java.io.IOException;

public class ApplicationMain {
  public static void main(String[] args) throws IOException, DetectorException {
    // Load dataset.
    HotelReviewsDataset hotelReviewsDataset = new HotelReviewsDataset("./corpus/op_spam_v1.4");
    OperationResult result = hotelReviewsDataset.load(123456, .5, .5);
    if (result.isSuccess()) {
      System.out.println("Successfully loaded the dataset");
    } else {
      System.err.println("Fail to load the dataset");
    }

    // Linguistic Feature extraction.
    DeceptionFeatureExtractor deceptionFeatureExtractor = new DeceptionFeatureExtractor();
    ObservationsList trainingDataset = hotelReviewsDataset.getTestingSet();
    AnalyticsBaseTable trainingABT = deceptionFeatureExtractor.CerateAnalyticsBaseTable(trainingDataset);

    // Dump for debugging
    //Exportable csvExportable = ExporterFactory.getExportable(ExportableTypes.csv);
    //csvExportable.export(trainingABT, "C:/temp/");

    // Start execution
    ExperimentExecuter experiment = ExperimentExecuter.getInstance();
    experiment.setup("./output", 1);

    Context context = experiment.getExperimentContext();
    context.setTrainingDataset(trainingABT);
    Executer gpExecutor = new Executer();
    gpExecutor.Execute(context);
  }
}
