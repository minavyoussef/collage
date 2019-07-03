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
import ie.ucd.mscba.nca.common.OperationResult;
import ie.ucd.mscba.nca.experiment.ExperimentExecuter;

import java.io.IOException;

public class Executer {
  public OperationResult Execute(Context context) throws DetectorException, IOException {
    DetectorException.throwIfNull(context, "context");

    OperationResult validationResult = context.validate();
    if (!validationResult.isSuccess()) {
      throw new DetectorException("[Executer]::[Validation failed (" + validationResult.getMessage() + ")]", validationResult);
    }

    ConvergenceTester tester = new ConvergenceTester(context);

    Population currentPopulation = Population.generateRandom(context);
    currentPopulation.calculateFitness(context);
    ExperimentExecuter.getInstance().serializePopulation(currentPopulation, tester.getCurrentIteration());

    do {
      context.setCurrentPopulation(currentPopulation);

      // TODO: Parallelization point.
      Cycle cycle = new Cycle();
      OperationResult cycleResult = cycle.run(context);
      if (!cycleResult.isSuccess()) {
        throw new DetectorException("[Executer]::[Execution failed]::" + cycleResult.getMessage(), cycleResult);
      }

      Population newPopulation = (Population) cycleResult.getRuntimeObject();
      newPopulation.calculateFitness(context);

      tester.update(currentPopulation, newPopulation);
      currentPopulation = newPopulation;

      ExperimentExecuter.getInstance().serializePopulation(newPopulation, tester.getCurrentIteration());
    } while (tester.hasNext());

    return new OperationResult();
  }
}
