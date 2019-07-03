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

import ie.ucd.mscba.nca.common.DetectorException;

import java.util.ArrayList;

public class AnalyticsBaseTableData extends ArrayList<AnalyticsBaseTableRow> {
  public <T extends Object> ArrayList<T> extractColumnDataByName(String name) {
    DetectorException.throwIfNull(name, "name");

    ArrayList<T> columnVector = new ArrayList<>();

    for (AnalyticsBaseTableRow rowIterator : this) {
      columnVector.add((T) rowIterator.get(name));
    }

    return columnVector;
  }
}
