/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.tm.cues.deception;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTable;
import ie.ucd.mscba.nca.abt.AnalyticsBaseTableRow;
import ie.ucd.mscba.nca.abt.common.ColumnDirection;
import ie.ucd.mscba.nca.abt.common.ColumnMetadata;
import ie.ucd.mscba.nca.abt.common.ColumnType;
import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.ds.Observationable;
import ie.ucd.mscba.nca.ds.ObservationsList;
import ie.ucd.mscba.nca.tm.cues.Cue;
import ie.ucd.mscba.nca.tm.cues.CuesList;

public class DeceptionFeatureExtractor {
  static {
    DefaultCuesList = DeceptionFeatureExtractor.createNewCuesList();
  }

  public AnalyticsBaseTable CerateAnalyticsBaseTable(ObservationsList dataset) {
    DetectorException.throwIfNull(dataset, "dataset");

    AnalyticsBaseTable table = new AnalyticsBaseTable();

    constructSchema(table);
    for (Observationable observationIterator : dataset) {
      AnalyticsBaseTableRow row = constructRow(observationIterator);
      try {
        table.add(row);
      } catch (DetectorException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    table.calculateStatistics();
    return table;
  }

  private void constructSchema(AnalyticsBaseTable table) {
    table.addColumn(new ColumnMetadata("RawText", "Raw Text", "Hotel customer review text", ColumnType.Text, ColumnDirection.Neutral));
    table.addColumn(new ColumnMetadata("WordsVector", "Words Vector", "Hotel customer review words vector", ColumnType.Collection, ColumnDirection.Neutral));
    table.addColumn(new ColumnMetadata("Deceptive", "Deceptive", "Label attribute flag if the review is deceptive or not", ColumnType.Boolean, ColumnDirection.Target));

    for (Cue cueIterator : DefaultCuesList) {
      table.addColumn(new ColumnMetadata(cueIterator.getName(), cueIterator.getFriendlyName(), cueIterator.getDescription(), ColumnType.Numeric, ColumnDirection.Input));
    }
  }

  private AnalyticsBaseTableRow constructRow(Observationable observation) {
    AnalyticsBaseTableRow row = new AnalyticsBaseTableRow();

    row.put("RawText", observation.getObservationRawText());
    row.put("WordsVector", observation.getStatementsWordsVector());
    row.put("Deceptive", observation.getDeceptionLevel() == DeceptionLevel.Deceptive);

    CuesList cueList = DeceptionFeatureExtractor.createNewCuesList();
    for (Cue cueIterator : cueList) {
      cueIterator.extractCue(observation);
      row.put(cueIterator.getName(), cueIterator.getValue());
    }

    return row;
  }

  private static CuesList createNewCuesList() {
    CuesList templateCueList = new CuesList();
    templateCueList.add(new CueNumberOfSentences());
    templateCueList.add(new CueNumberOfWords());
    templateCueList.add(new CueNumberOfComplexWords());
    templateCueList.add(new CueNumberOfSyllables());
    templateCueList.add(new CueNumberOfCharacters());
    templateCueList.add(new CueAvarageWordLength());

    templateCueList.add(new CueAutomatedReadabilityIndex());
    templateCueList.add(new CueSMOGReadability());
    templateCueList.add(new CueSMOGReadabilityIndex());
    templateCueList.add(new CueColemanLiauIndex());
    templateCueList.add(new CueGunningFogIndex());
    templateCueList.add(new CueFleschKincaidReadabilityIndex());
    templateCueList.add(new CueFleschReadabilityIndex());
    // TODO: Add cues

    return templateCueList;
  }

  private static CuesList DefaultCuesList;
}
