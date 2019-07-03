/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTable;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.OperationResult;
import ie.ucd.mscba.nca.gp.crossover.CrossoverTypes;
import ie.ucd.mscba.nca.gp.selection.SelectionTypes;

public class Context {
  public int getMaxIterations() {
    return maxIterations;
  }

  public double getTargetAccuracy() {
    return targetAccuracy;
  }

  public void setTargetAccuracy(double targetAccuracy) {
    this.targetAccuracy = targetAccuracy;
  }

  public double getThresholdDistance() {
    return thresholdDistance;
  }

  public void setThresholdDistance(double thresholdDistance) {
    this.thresholdDistance = thresholdDistance;
  }

  public OperationResult validate() {
    // TODO: Validate context consistency.
    return new OperationResult();
  }

  public void setMaxIterations(int maxIterations) {
    DetectorException.throwIfNegative(maxIterations);
    DetectorException.throwIfZero(maxIterations);

    this.maxIterations = maxIterations;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public void setPopulationSize(int populationSize) {
    DetectorException.throwIfNegative(maxIterations);
    DetectorException.throwIfZero(maxIterations);

    this.populationSize = populationSize;
  }

  public int getMaxModelDepth() {
    return maxModelDepth;
  }

  public void setMaxModelDepth(int maxModelDepth) {
    this.maxModelDepth = maxModelDepth;
  }

  public int getMinModelDepth() {
    return minModelDepth;
  }

  public void setMinModelDepth(int minModelDepth) {
    this.minModelDepth = minModelDepth;
  }

  public double getBranchToBoundRatio() {
    return branchToBoundRatio;
  }

  public void setBranchToBoundRatio(double branchToBoundRatio) {
    this.branchToBoundRatio = branchToBoundRatio;
  }

  public Population getCurrentPopulation() {
    return currentPopulation;
  }

  public void setCurrentPopulation(Population currentPopulation) {
    this.currentPopulation = currentPopulation;
  }

  public double getCorssoverProbability() {
    return corssoverProbability;
  }

  public void setCorssoverProbability(double corssoverProbability) {
    this.corssoverProbability = corssoverProbability;
  }

  public double getMutationProbability() {
    return mutationProbability;
  }

  public void setMutationProbability(double mutationProbability) {
    this.mutationProbability = mutationProbability;
  }

  public SelectionTypes getSelectionOperationType() {
    return selectionOperationType;
  }

  public void setSelectionOperationType(SelectionTypes selectionOperationType) {
    this.selectionOperationType = selectionOperationType;
  }

  public CrossoverTypes getCrossoverOperationType() {
    return crossoverOperationType;
  }

  public void setCrossoverOperationType(CrossoverTypes crossoverOperationType) {
    this.crossoverOperationType = crossoverOperationType;
  }

  public AnalyticsBaseTable getTrainingDataset() {
    return trainingDataset;
  }

  public void setTrainingDataset(AnalyticsBaseTable trainingDataset) {
    this.trainingDataset = trainingDataset;
  }

  private int maxIterations;
  private double targetAccuracy;
  private double thresholdDistance;
  private int populationSize;
  private int maxModelDepth;
  private int minModelDepth;
  private double branchToBoundRatio; // Represents how biased to randomize an internal node than terminal one.
  private Population currentPopulation;
  private double corssoverProbability;
  private double mutationProbability;
  private SelectionTypes selectionOperationType;
  private CrossoverTypes crossoverOperationType;
  private AnalyticsBaseTable trainingDataset;
}
