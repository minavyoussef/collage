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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatementsWordsVector extends Vector<WordsVector> {
  public void processRawText(String rawObservationText) {
    DetectorException.throwIfNullOrEmpty(rawObservationText, "rawObservationText");

    Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
    Matcher reMatcher = re.matcher(rawObservationText);
    while (reMatcher.find()) {
      WordsVector wordsVector = new WordsVector();
      wordsVector.processRawTextStatement(reMatcher.group().trim());
      add(wordsVector);
    }
  }

  public WordsVector flatten() {
    WordsVector flattenWordsVector = new WordsVector();

    for (WordsVector wordsVectorIterator : this) {
      for (String wordIterator : wordsVectorIterator) {
        flattenWordsVector.add(wordIterator);
      }
    }

    return flattenWordsVector;
  }
}
