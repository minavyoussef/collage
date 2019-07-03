/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.abt;

import ie.ucd.mscba.nca.abt.common.ColumnDirection;
import ie.ucd.mscba.nca.abt.common.ColumnMetadata;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AnalyticsBaseTableMetadata extends LinkedHashMap<String, ColumnMetadata> {
  public String[] getColumnsNameByDirection(ColumnDirection direction) {
    ArrayList<String> columnsNames = new ArrayList<String>();
    for (ColumnMetadata iterator : values()) {
      if (direction == iterator.getDirection()) {
        columnsNames.add(iterator.getName());
      }
    }

    String[] columnsNamesArray = new String[columnsNames.size()];
    columnsNamesArray = columnsNames.toArray(columnsNamesArray);

    return columnsNamesArray;
  }

  public String[] getColumnsName() {
    return keySet().toArray(new String[keySet().size()]);
  }
}
