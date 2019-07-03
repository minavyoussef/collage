/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.add.model;

public class ModelResult {
  public float get_deceptionScore() {
    return _deceptionScore;
  }

  public void set_deceptionScore(float _deceptionScore) {
    this._deceptionScore = _deceptionScore;
  }

  private float _deceptionScore;
}
