/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.ds.common;

import ie.ucd.mscba.nca.common.DetectorException;

import java.util.Vector;

public class WordsVector extends Vector<String> {
  public void processRawTextStatement(String fileContent) {
    DetectorException.throwIfNullOrEmpty(fileContent, "fileContent");

    // TODO: Optimization point
    fileContent = fileContent.replace("!", "");
    fileContent = fileContent.replace(",", " ");
    fileContent = fileContent.replace(";", " ");
    fileContent = fileContent.replace("n't", " not");

    String[] tokens = fileContent.split(" ");
    for (String tokenIterator : tokens) {
      String token = tokenIterator.trim();
      if (!token.isEmpty()) {
        add(tokenIterator.trim());
      }
    }
  }
}
