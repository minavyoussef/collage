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

import ie.ucd.mscba.nca.common.DeceptionLevel;

public class GPNodeTerminal extends GPNode {
  public GPNodeTerminal() {
    super(GPNodeType.Terminal);

    setDeceptionLevel(DeceptionLevel.Unknown);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    GPNodeTerminal clonedTerminal = new GPNodeTerminal();
    clonedTerminal.deceptionLevel = deceptionLevel;
    return clonedTerminal;
  }

  @Override
  public String toString() {
    return "( DeceptionLevel: " + deceptionLevel + ")";
  }

  public DeceptionLevel getDeceptionLevel() {
    return deceptionLevel;
  }

  public void setDeceptionLevel(DeceptionLevel deceptionLevel) {
    this.deceptionLevel = deceptionLevel;
  }

  private DeceptionLevel deceptionLevel;
}
