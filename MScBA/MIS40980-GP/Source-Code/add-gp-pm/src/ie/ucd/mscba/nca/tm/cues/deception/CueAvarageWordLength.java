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

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.ds.Observationable;
import ie.ucd.mscba.nca.ds.common.WordsVector;
import ie.ucd.mscba.nca.tm.cues.Cue;
import ie.ucd.mscba.nca.tm.cues.CueValueType;

public class CueAvarageWordLength extends Cue {
  public CueAvarageWordLength() {
    super("AvarageWordLength", "Avarage Word Length", "Avarage word length in a given textual observation", CueValueType.Real);
  }

  @Override
  public void extractCue(Observationable observation) {
    DetectorException.throwIfNull(observation, "observation");

    WordsVector wordsVector = observation.getStatementsWordsVector().flatten();
    if (wordsVector.size() == 0) {
      setValue((Object) 0.0);
      return;
    }

    long sum = 0L;
    for (String word : wordsVector) {
      sum += word.length();
    }
    setValue(((double) sum) / ((double) wordsVector.size()));
  }

  @Override
  public String toString() {
    return Utilites.serializeDouble(getValue());
  }
}
