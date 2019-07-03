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

import ie.ucd.mscba.nca.common.DetectorException;

public class ConvergenceTester {
  public ConvergenceTester(Context context) {
    DetectorException.throwIfNull(context, "context");

    currentIteration = 0;
    MaximumIterations = context.getMaxIterations();
    targetAccuracy = context.getTargetAccuracy();
    thresholdDistance = context.getThresholdDistance();
  }

  public void update(Population oldPopulation, Population newPopulaion) {
    DetectorException.throwIfNull(oldPopulation, "oldPopulation");
    DetectorException.throwIfNull(newPopulaion, "newPopulaion");

    ++currentIteration;
    currentMaxAccuracy = newPopulaion.getMaxFit();
    currentDistance = oldPopulation.calcuateDistance(newPopulaion);
  }

  public int getCurrentIteration() {
    return currentIteration;
  }

  public boolean hasNext() {
    if (currentIteration > MaximumIterations) {
      return false;
    } else if (currentMaxAccuracy >= targetAccuracy) {
      return false;
    }
    /*
    else if (currentDistance < thresholdDistance) {
      return false;
    }
    */
    return true;
  }
  private int currentIteration;
  private int MaximumIterations;

  private double currentMaxAccuracy;
  private double targetAccuracy;

  private double currentDistance;
  private double thresholdDistance;
}
