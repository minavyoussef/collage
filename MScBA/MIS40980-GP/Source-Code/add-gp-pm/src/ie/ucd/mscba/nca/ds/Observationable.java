/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.ds;

import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.ds.common.AttributesTable;
import ie.ucd.mscba.nca.ds.common.StatementsWordsVector;

public interface Observationable {
  String getObservationRawText();
  StatementsWordsVector getStatementsWordsVector();
  AttributesTable getAttributes();
  DeceptionLevel getDeceptionLevel();
}
