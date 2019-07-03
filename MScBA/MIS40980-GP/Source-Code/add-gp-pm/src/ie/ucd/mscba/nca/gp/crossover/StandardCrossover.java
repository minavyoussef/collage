/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.crossover;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.gp.Chromosome;
import ie.ucd.mscba.nca.gp.Offsprings;
import ie.ucd.mscba.nca.gp.tree.GPNode;
import ie.ucd.mscba.nca.gp.tree.GPNodeInternal;

public class StandardCrossover implements Crossable {

  @Override
  public Offsprings crossover(Chromosome parentX, Chromosome parentY) throws DetectorException {
    DetectorException.throwIfNull(parentX, "parentX");
    DetectorException.throwIfNull(parentY, "parentY");

    Chromosome offspringX;
    Chromosome offspringY;
    try {
      offspringX = (Chromosome) parentX.clone();
      offspringY = (Chromosome) parentY.clone();
    } catch (CloneNotSupportedException e) {
      throw new DetectorException("[StandardCrossover]::{fail to clone parent chromosome(s)}");
    }

    GPNodeInternal internalNodeX = offspringX.getModelTree().pickRandomInternalNode();
    GPNodeInternal internalNodeY = offspringY.getModelTree().pickRandomInternalNode();

    if (internalNodeX != null && internalNodeY != null) {
      GPNode temp = internalNodeX.getFalseBranch();
      internalNodeX.setFalseBranch(internalNodeY.getTrueBranch());
      internalNodeY.setTrueBranch(temp);
    }

    return new Offsprings(offspringX, offspringY);
  }

}
