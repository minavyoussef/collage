/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.experiment;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.gp.Context;
import ie.ucd.mscba.nca.gp.Population;
import ie.ucd.mscba.nca.gp.crossover.CrossoverTypes;
import ie.ucd.mscba.nca.gp.selection.SelectionTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class ExperimentExecuter {
  private ExperimentExecuter() {
    contexts = new ArrayList<Context>();

    Context experimentContext;
    experimentContext = getNewDefaultContext();
    contexts.add(experimentContext);
  }

  public static ExperimentExecuter getInstance() {
    return singleInstance == null ? singleInstance = new ExperimentExecuter() : singleInstance;
  }

  public Context getNewDefaultContext() {
    Context defaultContext = new Context();
    defaultContext.setMaxIterations(500);
    defaultContext.setTargetAccuracy(.8);
    defaultContext.setThresholdDistance(0.005);
    defaultContext.setPopulationSize(1000);
    defaultContext.setMinModelDepth(5);
    defaultContext.setMaxModelDepth(10);
    defaultContext.setBranchToBoundRatio(.85);
    defaultContext.setMutationProbability(.1);
    defaultContext.setCorssoverProbability(1.0);
    defaultContext.setSelectionOperationType(SelectionTypes.Tournament);
    defaultContext.setCrossoverOperationType(CrossoverTypes.Standard);
    return defaultContext;
  }

  public void setup(String outputPath, int id) throws DetectorException {
    DetectorException.throwIfNull(outputPath, "outputPath");

    experimentId = id;
    experimentOutputPath = Paths.get(outputPath, "Experiment-" + id).toString();

    Utilites.createDirectoryIfNotExists(experimentOutputPath);
  }

  public Context getExperimentContext() {
    return contexts.get(experimentId - 1);
  }

  public void serializePopulation(Population population, int populationNumber) throws IOException {
    DetectorException.throwIfNull(population, "population");

    serializePopulationContent(population, populationNumber);
    serializePopulationStatistics(population);
  }
  private void serializePopulationContent(Population population, int populationNumber) throws IOException {
    String fileName = Paths.get(experimentOutputPath, String.format("Population-%03d.csv", populationNumber)).toString();
    FileWriter fileWriter = new FileWriter(fileName);
    fileWriter.write(String.format("ID                                      Fitness  Deception Model\n"));
    Hashtable<UUID, Double> fitnessCache = population.getFitnessCache();
    for (UUID chromosomeId : fitnessCache.keySet()) {
      StringBuffer entry = new StringBuffer();
      entry.append(chromosomeId.toString() + "\t");
      entry.append(String.format("%.2f%%\t", fitnessCache.get(chromosomeId).doubleValue() * 100.0));
      entry.append(Utilites.limitString(population.get(chromosomeId).toString(), 150) + "\n");

      fileWriter.write(entry.toString());
    }
    fileWriter.close();
  }

  private void serializePopulationStatistics(Population population) throws IOException {
    String fileName = Paths.get(experimentOutputPath, String.format("Population-Fitness-Analytics.csv")).toString();
    if (!(new File(fileName).exists())) {
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(String.format("Maximum Fitness,Min Fitness,Avarage Fitness\n"));
      fileWriter.close();
    }

    FileWriter fileWriter = new FileWriter(fileName, true);
    fileWriter.write(String.format("%.2f,%.2f,%.2f\n", population.getMaxFit() * 100, population.getMinFit() * 100, population.getAvaragePopulationFitness() * 100));
    fileWriter.close();
  }

  private static ExperimentExecuter singleInstance;

  private ArrayList<Context> contexts;
  private int experimentId;
  private String experimentOutputPath;
}
