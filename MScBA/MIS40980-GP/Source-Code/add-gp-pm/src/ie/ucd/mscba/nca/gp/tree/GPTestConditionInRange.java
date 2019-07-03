/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.tree;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTableRow;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Pair;

public class GPTestConditionInRange implements GPTestableCondition {
  public GPTestConditionInRange() {
    min = max = 0.0;
    operand = Pair.of("", 0.0);
  }

  @Override
  public boolean evaluate() throws DetectorException {
    DetectorException.throwIfNullOrEmpty(operand.first, "operand");

    double value = operand.second;
    return (value >= min && value <= max);
  }

  @Override
  public void bind(AnalyticsBaseTableRow observation) throws DetectorException {
    DetectorException.throwIfNull(observation, "observation");

    String attributeName = operand.first;
    if (!observation.containsKey(attributeName)) {
      throw new DetectorException("[GP]::[GPTestConditionInRange]::[Bind]::[Missing attribute]");
    }

    operand = Pair.of(operand.first, (Double) observation.get(attributeName));
  }

  @Override
  public void unbind() {
    operand = Pair.of(operand.first, 0.0);
  }

  @Override
  public GPTestableCondition cloneTestabilityPoint() {
    GPTestConditionInRange clonedInRangeCondition = new GPTestConditionInRange();
    clonedInRangeCondition.min = min;
    clonedInRangeCondition.operand = Pair.of(operand.first, operand.second);
    clonedInRangeCondition.max = max;

    return clonedInRangeCondition;
  }

  @Override
  public String toString() {
    return "({" + operand.first + ":" + operand.second + "} in [" + min + ", " + max + "])";
  }

  public double getMin() {
    return min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public Pair<String, Double> getOperand() {
    return operand;
  }

  public void setOperand(Pair<String, Double> operand) {
    this.operand = operand;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  private double min;
  private Pair<String, Double> operand;
  private double max;
}
