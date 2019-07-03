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
import ie.ucd.mscba.nca.abt.common.ColumnType;
import ie.ucd.mscba.nca.abt.common.StatisticalSummary;
import ie.ucd.mscba.nca.common.DetectorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalyticsBaseTable {
  public AnalyticsBaseTable() {
    data = new AnalyticsBaseTableData();
    metadata = new AnalyticsBaseTableMetadata();
    annotation = new AnalyticsBaseTableAnnotation();
  }

  public void addColumn(ColumnMetadata columnMetadata) {
    DetectorException.throwIfNull(columnMetadata, "columnMetadata");

    metadata.put(columnMetadata.getName(), columnMetadata);
    if (columnMetadata.getType() == ColumnType.Numeric) {
      annotation.put(columnMetadata.getName(), new StatisticalSummary());
    }
  }

  public void add(AnalyticsBaseTableRow row) throws DetectorException {
    DetectorException.throwIfNull(row, "row");

    // TODO: Support table integrate.
    // Integer hashRawText = row.hashCode();
    // if (data.containsKey(hashRawText)) {
    //   throw new DetectorException("Observation already present in the ABT " + entry.getObservation().toString());
    // }

    if (row.confirm(metadata)) {
      data.add(row);
    }
  }

  public List<AnalyticsBaseTableRow> getObservations() {
    return data;
  }

  public String[] getColumnsName() {
    return metadata.getColumnsName();
  }

  public String[] getInputColumnsName() {
    return metadata.getColumnsNameByDirection(ColumnDirection.Input);
  }

  public String getLabelColumnName() throws DetectorException {
    String[] labelColumns = metadata.getColumnsNameByDirection(ColumnDirection.Target);
    if (labelColumns.length == 0) {
      throw new DetectorException("[AnalyticsBaseTable]::[No label column is set]");
    } else if (labelColumns.length > 1) {
      throw new DetectorException("[AnalyticsBaseTable]::[multiple label columns are set]");
    } else {
      return labelColumns[0];
    }
  }

  public ColumnMetadata getColumnMetadata(String columnName) {
    DetectorException.throwIfNull(columnName, "column");
    return metadata.get(columnName);
  }

  public StatisticalSummary getStatisticalSummary(String columnName) {
    DetectorException.throwIfNull(columnName, "column");
    return annotation.get(columnName);
  }

  public void calculateStatistics() {
    Iterator<ColumnMetadata> columnIterator = metadata.values().iterator();
    while (columnIterator.hasNext()) {
      ColumnMetadata columnMetadata = columnIterator.next();
      if (columnMetadata.getType() != ColumnType.Numeric) {
        continue;
      }

      calculateStatisticsByColumnName(columnMetadata.getName());
    }
  }

  private void calculateStatisticsByColumnName(String name) {
    ArrayList<Double> columnContent = data.extractColumnDataByName(name);

    StatisticalSummary statisticalSummary = new StatisticalSummary();
    statisticalSummary.calculate(columnContent);

    annotation.put(name, statisticalSummary);
  }

  private AnalyticsBaseTableData data;
  private AnalyticsBaseTableMetadata metadata;
  private AnalyticsBaseTableAnnotation annotation;
}
