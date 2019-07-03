/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.abt.common;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticalSummary {
  public void calculate(ArrayList<Double> columnContent) {
    DetectorException.throwIfNull(columnContent, "columnContent");

    columnContent.sort(new Comparator<Double>() {
      @Override
      public int compare(Double d1, Double d2) {
        return Double.compare(d1, d2);
      }
    });

    calculateMin(columnContent);
    calculateMax(columnContent);
    calculateMean(columnContent);
    calculateMedian(columnContent);
    calculateMode(columnContent);
    calculateStandardDeviation(columnContent);
  }

  private void calculateMin(ArrayList<Double> columnContent) {
    setMin(columnContent.get(0));
  }

  private void calculateMax(ArrayList<Double> columnContent) {
    setMax(columnContent.get(columnContent.size() - 1));
  }

  private void calculateMean(ArrayList<Double> columnContent) {
    setMean(columnContent.stream().mapToDouble(a -> a).average().getAsDouble());
  }

  private void calculateMedian(ArrayList<Double> columnContent) {
    int centroid = (int) Math.floor(columnContent.size() / 2);
    setMedian(columnContent.get(centroid));
  }

  private void calculateMode(ArrayList<Double> columnContent) {
    // TODO Implement mode
  }

  private void calculateStandardDeviation(ArrayList<Double> columnContent) {
    // TODO Implement SD
  }

  public double getMin() {
    return min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public double getMean() {
    return mean;
  }

  public void setMean(double mean) {
    this.mean = mean;
  }

  public double getMedian() {
    return median;
  }

  public void setMedian(double median) {
    this.median = median;
  }

  public double getMode() {
    return mode;
  }

  public void setMode(double mode) {
    this.mode = mode;
  }

  public double getStandardDeviation() {
    return standardDeviation;
  }

  public void setStandardDeviation(double standardDeviation) {
    this.standardDeviation = standardDeviation;
  }

  public double getRange() {
    return max - min;
  }

  public double randomize() {
    // TODO: Investigate other randomization techniques based on data distribution.
    return Utilites.randomize(min, max);
  }

  @Override
  public String toString() {
    return String.format("[min: %.2f, max: %.2f]", min, max);
  }

  private double min;
  private double max;
  private double mean;
  private double median;
  private double mode;
  private double standardDeviation;
}
