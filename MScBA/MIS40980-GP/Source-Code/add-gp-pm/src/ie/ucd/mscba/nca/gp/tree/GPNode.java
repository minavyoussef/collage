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

public class GPNode {
  public GPNode(GPNodeType type) {
    this.type = type;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new GPNode(type);
  }

  public GPNodeType getNodeType() {
    return type;
  }

  private GPNodeType type;
}
