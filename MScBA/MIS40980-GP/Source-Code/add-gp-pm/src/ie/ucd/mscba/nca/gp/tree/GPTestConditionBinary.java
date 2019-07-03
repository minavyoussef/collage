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

public class GPTestConditionBinary implements GPTestableCondition {
  public GPTestConditionBinary() {
    lhs = Pair.of("", 0.0);
    operator = GPTestConditionOperatorsType.Unknown;
    rhs = 0.0;
  }

  @Override
  public boolean evaluate() throws DetectorException {
    double lhsValue = lhs.second;

    switch (operator) {
      case LT :
        return lhsValue < rhs;
      case LE :
        return lhsValue <= rhs;
      case GT :
        return lhsValue > rhs;
      case GE :
        return lhsValue >= rhs;
      case EQ :
        return lhsValue == rhs;
      case NE :
        return lhsValue != rhs;

      default :
        throw new DetectorException("Unknow operator");
    }
  }

  @Override
  public void bind(AnalyticsBaseTableRow observation) throws DetectorException {
    DetectorException.throwIfNull(observation, "observation");

    String attributeName = lhs.first;
    if (!observation.containsKey(attributeName)) {
      throw new DetectorException("[GP]::[GPTestConditionBinary]::[Bind]::[Missing attribute]");
    }

    lhs = Pair.of(lhs.first, (Double) observation.get(attributeName));
  }

  @Override
  public void unbind() {
    lhs = Pair.of(lhs.first, 0.0);
  }

  @Override
  public GPTestableCondition cloneTestabilityPoint() {
    GPTestConditionBinary testConditionBinary = new GPTestConditionBinary();
    testConditionBinary.lhs = Pair.of(lhs.first, lhs.second);
    testConditionBinary.operator = operator;
    testConditionBinary.rhs = rhs;
    return testConditionBinary;
  }

  @Override
  public String toString() {
    return "({" + lhs.first + ":" + lhs.second + "} " + operator.toString() + " " + rhs + ")";
  }

  public GPTestConditionOperatorsType getOperator() {
    return operator;
  }

  public void setOperator(GPTestConditionOperatorsType operator) {
    this.operator = operator;
  }

  public Pair<String, Double> getLhs() {
    return lhs;
  }

  public void setLhs(Pair<String, Double> lhs) {
    this.lhs = lhs;
  }

  public double getRhs() {
    return rhs;
  }

  public void setRhs(double rhs) {
    this.rhs = rhs;
  }

  private Pair<String, Double> lhs;
  private GPTestConditionOperatorsType operator;
  private double rhs;
}
