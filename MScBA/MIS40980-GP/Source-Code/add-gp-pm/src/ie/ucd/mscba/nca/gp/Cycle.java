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
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.gp.common.ComponentFactory;
import ie.ucd.mscba.nca.gp.crossover.Crossable;
import ie.ucd.mscba.nca.gp.mutation.Mutationable;
import ie.ucd.mscba.nca.gp.selection.Selectable;

public class Cycle {
  public OperationResult run(Context context) throws DetectorException {
    DetectorException.throwIfNull(context, "context");

    Population offspringsPopulation = new Population();
    Population parentsPopulation = context.getCurrentPopulation();

    int halfPopulationSize = (context.getPopulationSize() / 2) - 1;
    do {
      // 1. Selection.
      Selectable selector = ComponentFactory.getSelectionOperation(context.getSelectionOperationType());
      OperationResult selectionResult = selector.select(parentsPopulation);
      if (!selectionResult.isSuccess()) {
        throw new DetectorException("[Cycle]::[Selection Failed]", selectionResult);
      }

      // 2. Crossover.
      Chromosome[] parents = (Chromosome[]) selectionResult.getRuntimeObject();
      Chromosome parentX = parents[0];
      Chromosome parentY = parents[1];
      Offsprings offsprings = null;
      if (applyOperator(context.getCorssoverProbability())) {
        // Perform crossover.
        Crossable crossover = ComponentFactory.getCrossoverOperation(context.getCrossoverOperationType());
        offsprings = crossover.crossover(parents[0], parents[1]);
      } else {
        // Random copy one of the parents.
        offsprings = new Offsprings(parentX, parentY);
      }

      // 3. Mutation.
      if (applyOperator(context.getMutationProbability())) {
        Mutationable mutation = ComponentFactory.getMutationOperation();
        offsprings = mutation.mutate(offsprings, context);
      }

      offspringsPopulation.add(offsprings.getChildX());
      offspringsPopulation.add(offsprings.getChildY());
    } while (halfPopulationSize-- > 0);

    return new OperationResult(offspringsPopulation);
  }

  private boolean applyOperator(double operationProbability) {
    int randomPercentage = Utilites.randomize(0, 100);
    int crossoverPercentage = (int) (operationProbability * 100.0);

    return randomPercentage <= crossoverPercentage;
  }
}
