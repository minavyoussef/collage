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
import ie.ucd.mscba.nca.abt.common.StatisticalSummary;
import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Pair;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.gp.tree.GPNode;
import ie.ucd.mscba.nca.gp.tree.GPNodeInternal;
import ie.ucd.mscba.nca.gp.tree.GPNodeTerminal;
import ie.ucd.mscba.nca.gp.tree.GPTestConditionBinary;
import ie.ucd.mscba.nca.gp.tree.GPTestConditionInRange;
import ie.ucd.mscba.nca.gp.tree.GPTestConditionOperatorsType;
import ie.ucd.mscba.nca.gp.tree.GPTestableCondition;
import ie.ucd.mscba.nca.gp.tree.GPTree;

import java.util.UUID;

public class Chromosome {
  private Chromosome() {
    id = UUID.randomUUID();
  }

  public static Chromosome createRandom(Context context) {
    DetectorException.throwIfNull(context, "context");

    Chromosome chromosome = new Chromosome();
    chromosome.modelTree = randomizedTree(context);
    return chromosome;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Chromosome clonedChromosome = new Chromosome();
    clonedChromosome.modelTree = (GPTree) modelTree.clone();

    return clonedChromosome;
  }

  @Override
  public String toString() {
    return modelTree.toString();
  }

  public double calculateFitness(Context context) {
    DetectorException.throwIfNull(context, "context");
    AnalyticsBaseTable abt = context.getTrainingDataset();

    return modelTree.calculateFitness(abt);
  }

  public GPTree getModelTree() {
    return modelTree;
  }

  public void setModelTree(GPTree modelTree) {
    this.modelTree = modelTree;
  }

  public UUID getId() {
    return id;
  }

  private static GPTree randomizedTree(Context context) {
    GPTree tree = new GPTree();
    tree.setRoot(randomizeNode(context, 0));
    return tree;
  }

  private static GPNode randomizeNode(Context context, int depth) {
    int maxDepth = context.getMaxModelDepth();
    int minDepth = context.getMinModelDepth();

    if (depth >= (maxDepth - 1)) {
      GPNodeTerminal terminalRandomizedNode = new GPNodeTerminal();
      terminalRandomizedNode.setDeceptionLevel(DeceptionLevel.randomize());
      return terminalRandomizedNode;
    } else {
      if (depth < minDepth) {
        // Internal node randomization only.
        GPNodeInternal internalRandomizedNode = new GPNodeInternal();
        internalRandomizedNode.setTestPoint(randomizeTestPoint(context));
        internalRandomizedNode.setFalseBranch(randomizeNode(context, depth + 1));
        internalRandomizedNode.setTrueBranch(randomizeNode(context, depth + 1));
        return internalRandomizedNode;
      } else {
        GPNode randomizedNode = null;

        double branchToBoundRation = context.getBranchToBoundRatio();
        boolean internalNode = Utilites.randomizeBinaryWithBiased(branchToBoundRation);
        if (internalNode) {
          // Internal node randomization.
          GPNodeInternal internalRandomizedNode = new GPNodeInternal();
          internalRandomizedNode.setTestPoint(randomizeTestPoint(context));
          internalRandomizedNode.setFalseBranch(randomizeNode(context, depth + 1));
          internalRandomizedNode.setTrueBranch(randomizeNode(context, depth + 1));

          randomizedNode = internalRandomizedNode;
        } else {
          // Terminal node randomization.
          GPNodeTerminal terminalRandomizedNode = new GPNodeTerminal();
          terminalRandomizedNode.setDeceptionLevel(DeceptionLevel.randomize());

          randomizedNode = terminalRandomizedNode;
        }

        return randomizedNode;
      }
    }
  }
  private static GPTestableCondition randomizeTestPoint(Context context) {
    boolean binaryCondition = Utilites.randomizeBinary();
    GPTestableCondition testCondition = binaryCondition ? randomizeBinaryTestCondition(context) : randomizeInRangeTestCondition(context);
    return testCondition;
  }

  private static GPTestableCondition randomizeBinaryTestCondition(Context context) {
    GPTestConditionBinary binaryTestCondition = new GPTestConditionBinary();

    AnalyticsBaseTable abt = context.getTrainingDataset();
    String randomInputVariable = Utilites.randomizeZeroBasedArray(abt.getInputColumnsName());
    StatisticalSummary randomInputVariableStatisticalSummary = abt.getStatisticalSummary(randomInputVariable);

    binaryTestCondition.setLhs(Pair.of(randomInputVariable, 0.0));
    binaryTestCondition.setOperator(GPTestConditionOperatorsType.randomize());
    binaryTestCondition.setRhs(randomInputVariableStatisticalSummary.randomize());
    return binaryTestCondition;
  }

  private static GPTestableCondition randomizeInRangeTestCondition(Context context) {
    GPTestConditionInRange rangeTestCondition = new GPTestConditionInRange();

    AnalyticsBaseTable abt = context.getTrainingDataset();
    String randomInputVariable = Utilites.randomizeZeroBasedArray(abt.getInputColumnsName());
    StatisticalSummary randomInputVariableStatisticalSummary = abt.getStatisticalSummary(randomInputVariable);

    double x = 0.0;
    double y = 0.0;

    do {
      x = Utilites.randomize(randomInputVariableStatisticalSummary.getMax(), randomInputVariableStatisticalSummary.getMin());
      y = Utilites.randomize(randomInputVariableStatisticalSummary.getMax(), randomInputVariableStatisticalSummary.getMin());
    } while (x == y);

    rangeTestCondition.setMin(Math.min(x, y));
    rangeTestCondition.setOperand(Pair.of(randomInputVariable, 0.0));
    rangeTestCondition.setMax(Math.max(x, y));
    return rangeTestCondition;
  }

  private UUID id;
  private GPTree modelTree;
}
