/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.mutation;

import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.gp.Chromosome;
import ie.ucd.mscba.nca.gp.Context;
import ie.ucd.mscba.nca.gp.Offsprings;
import ie.ucd.mscba.nca.gp.tree.GPNode;
import ie.ucd.mscba.nca.gp.tree.GPNodeInternal;
import ie.ucd.mscba.nca.gp.tree.GPNodeTerminal;
import ie.ucd.mscba.nca.gp.tree.GPTree;

public class Mutation implements Mutationable {
  @Override
  public Offsprings mutate(Offsprings offsprings, Context context) {
    DetectorException.throwIfNull(context, "context");

    Chromosome chromosomeX = offsprings.getChildX();
    Chromosome chromosomeY = offsprings.getChildY();

    boolean randomMutateInternalOrTerminal = Utilites.randomizeBinary();
    if (randomMutateInternalOrTerminal) {
      // Terminal Node.
      mutateTerminal(chromosomeX.getModelTree());
      mutateTerminal(chromosomeY.getModelTree());
    } else {
      mutateInternal(chromosomeX.getModelTree());
      mutateInternal(chromosomeY.getModelTree());
    }

    return offsprings;
  }

  private void mutateInternal(GPTree modelTree) {
    GPNodeInternal node = modelTree.pickRandomInternalNode();
    GPNode temp = node.getTrueBranch();
    node.setTrueBranch(node.getFalseBranch());
    node.setFalseBranch(temp);
  }

  private void mutateTerminal(GPTree modelTree) {
    GPNodeTerminal node = modelTree.pickRandomTerminalNode();
    node.setDeceptionLevel(node.getDeceptionLevel() == DeceptionLevel.Deceptive ? DeceptionLevel.Truthful : DeceptionLevel.Deceptive);
  }
}
