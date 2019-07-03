/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.abt.common;

public class ColumnMetadata {
  public ColumnMetadata(String name, String friendlyName, String description, ColumnType type, ColumnDirection direction) {
    this.name = name;
    this.friendlyName = friendlyName;
    this.description = description;
    this.type = type;
    this.direction = direction;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ColumnType getType() {
    return type;
  }

  public void setType(ColumnType type) {
    this.type = type;
  }

  public ColumnDirection getDirection() {
    return direction;
  }

  public void setDirection(ColumnDirection direction) {
    this.direction = direction;
  }

  private String name;
  private String friendlyName;
  private String description;
  private ColumnType type;
  private ColumnDirection direction;
}
