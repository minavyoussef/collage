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

public class Offsprings {
  public Offsprings(Chromosome childX, Chromosome childY) {
    this.childX = childX;
    this.childY = childY;
  }

  public Chromosome getChildX() {
    return childX;
  }

  public Chromosome getChildY() {
    return childY;
  }

  private Chromosome childX;
  private Chromosome childY;
}
