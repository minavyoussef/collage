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

import ie.ucd.mscba.nca.ds.Observationable;

// TODO: [Design] ~ Split Cue representation from value.
public abstract class Cue {
  public Cue(String name, String friendlyName, String description, CueValueType valueType) {
    super();
    this.name = name;
    this.friendlyName = friendlyName;
    this.description = description;
    this.valueType = valueType;
  }

  public abstract void extractCue(Observationable observation);

  public String getName() {
    return name;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public String getDescription() {
    return description;
  }

  public CueValueType getValueType() {
    return valueType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  private String name;
  private String friendlyName;
  private String description;
  private CueValueType valueType;
  private Object value;
}
