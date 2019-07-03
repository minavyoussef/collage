/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.tm.cues;

public class CueValue {
  public CueValue(CueValueType type) {
    super();
    this.type = type;
    this.value = new Object();
  }

  public CueValue() {
    this(CueValueType.Unknown);
  }

  public CueValueType getType() {
    return type;
  }

  public void setType(CueValueType type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  private CueValueType type;
  private Object value;
}
