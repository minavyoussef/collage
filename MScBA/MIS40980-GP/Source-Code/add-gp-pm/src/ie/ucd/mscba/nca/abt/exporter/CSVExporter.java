/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.abt.exporter;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTable;
import ie.ucd.mscba.nca.abt.AnalyticsBaseTableRow;
import ie.ucd.mscba.nca.abt.common.ColumnMetadata;
import ie.ucd.mscba.nca.abt.common.ColumnType;
import ie.ucd.mscba.nca.abt.common.StatisticalSummary;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CSVExporter implements Exportable {
  @Override
  public void export(AnalyticsBaseTable abt, Object outputMedia) throws IOException {
    DetectorException.throwIfNull(abt, "abt");
    DetectorException.throwIfNull(outputMedia, "outputMedia");

    String outputDirectory = (String) outputMedia;
    DetectorException.throwIfPathNotExists(outputDirectory);
    DetectorException.throwIfPathNotDirectory(outputDirectory);

    serializeABT(abt, outputDirectory);
    serializeABTSummary(abt, outputDirectory);
  }

  private void serializeABT(AnalyticsBaseTable abt, String outputDirector) throws IOException {
    Utilites.deleteIfExists(Paths.get(outputDirector, ABT_FILE_NAME));

    String FileAbsolutePath = Paths.get(outputDirector, ABT_FILE_NAME).toAbsolutePath().toString();
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FileAbsolutePath)));

    writer.write(serializeHeader(abt));
    writer.newLine();
    for (AnalyticsBaseTableRow row : abt.getObservations()) {
      writer.write(serializeRow(abt, row));
      writer.newLine();
    }

    writer.flush();
    writer.close();
  }

  private String serializeHeader(AnalyticsBaseTable abt) {
    ArrayList<String> headerValues = new ArrayList<>();
    String[] columnsName = abt.getColumnsName();
    for (String columnName : columnsName) {
      headerValues.add(String.format(FORMAT_TEXT, columnName));
    }

    return String.join(SEPARATOR, headerValues);
  }

  private String serializeRow(AnalyticsBaseTable abt, AnalyticsBaseTableRow row) {
    ArrayList<String> rowValues = new ArrayList<>();
    String[] columnsName = abt.getColumnsName();
    for (String columnName : columnsName) {
      String value = Utilites.limitString(row.get(columnName).toString(), TEXT_LIMIT);
      rowValues.add(String.format(FORMAT_TEXT, value));
    }
    return String.join(SEPARATOR, rowValues);
  }

  private void serializeABTSummary(AnalyticsBaseTable abt, String outputDirector) throws IOException {
    Utilites.deleteIfExists(Paths.get(outputDirector, ABT_SUMMERY_FILE_NAME));

    String FileAbsolutePath = Paths.get(outputDirector, ABT_SUMMERY_FILE_NAME).toAbsolutePath().toString();
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FileAbsolutePath)));

    writer.write(serializeHeaderSummary());
    writer.newLine();
    for (String column : abt.getColumnsName()) {
      writer.write(serializeRowSummary(abt, column));
      writer.newLine();
    }

    writer.flush();
    writer.close();
  }

  private String serializeHeaderSummary() {
    ArrayList<String> rowValues = new ArrayList<>();
    for (String headerName : SUMMERY_HEADER_VALUES) {
      rowValues.add(String.format(FORMAT_TEXT, headerName));
    }
    return String.join(SEPARATOR, rowValues);
  }

  private String serializeRowSummary(AnalyticsBaseTable abt, String columnName) {
    ArrayList<String> rowValues = new ArrayList<>();

    ColumnMetadata metadata = abt.getColumnMetadata(columnName);
    serializeRowSummaryField(rowValues, metadata.getName());
    serializeRowSummaryField(rowValues, metadata.getFriendlyName());
    serializeRowSummaryField(rowValues, metadata.getDescription());
    serializeRowSummaryField(rowValues, metadata.getType());
    serializeRowSummaryField(rowValues, metadata.getDirection());

    if (metadata.getType() == ColumnType.Numeric) {
      StatisticalSummary summary = abt.getStatisticalSummary(columnName);
      serializeRowSummaryField(rowValues, summary);
    }

    return String.join(SEPARATOR, rowValues);
  }

  private void serializeRowSummaryField(ArrayList<String> rowValues, Object value) {
    String formatedValue = Utilites.limitString(value.toString(), TEXT_LIMIT);
    rowValues.add(String.format(FORMAT_TEXT, formatedValue));
  }

  private static final String ABT_FILE_NAME = "AnalyticalBaseTable.csv";
  private static final String ABT_SUMMERY_FILE_NAME = "AnalyticalBaseTable-Summery.csv";
  private static final String FORMAT_TEXT = "%30s";
  private static final int TEXT_LIMIT = 30; // Must match the FORMAT_TEXT
  private static final String SEPARATOR = "\t";
  private static final String[] SUMMERY_HEADER_VALUES = new String[]{"Name", "Friendly Name", "Description", "Type", "Direction", "Statistics"};
}
