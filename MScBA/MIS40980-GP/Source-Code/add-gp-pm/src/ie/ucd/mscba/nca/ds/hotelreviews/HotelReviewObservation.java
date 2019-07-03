/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.ds.hotelreviews;

import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.ds.Observationable;
import ie.ucd.mscba.nca.ds.common.AttributesTable;
import ie.ucd.mscba.nca.ds.common.StatementsWordsVector;

public class HotelReviewObservation implements Observationable {
  public HotelReviewObservation() {
    rawText = "";
    statementsWordsVector = new StatementsWordsVector();
    attributes = new AttributesTable();
  }

  public void setRawText(String rawText) {
    this.rawText = rawText;
  }

  @Override
  public String getObservationRawText() {
    return rawText;
  }

  @Override
  public StatementsWordsVector getStatementsWordsVector() {
    return statementsWordsVector;
  }

  @Override
  public AttributesTable getAttributes() {
    return attributes;
  }

  public void setDeceptionLevel(DeceptionLevel deceptionLevel) {
    this.deceptionLevel = deceptionLevel;
  }

  @Override
  public DeceptionLevel getDeceptionLevel() {
    return deceptionLevel;
  }

  private String rawText;
  private StatementsWordsVector statementsWordsVector;
  private AttributesTable attributes;
  private DeceptionLevel deceptionLevel;
}
