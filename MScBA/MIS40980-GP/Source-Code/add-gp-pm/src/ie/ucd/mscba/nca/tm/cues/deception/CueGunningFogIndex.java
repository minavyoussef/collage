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
import ie.ucd.mscba.nca.tm.cues.Cue;
import ie.ucd.mscba.nca.tm.cues.CueValueType;
import ie.ucd.mscba.nca.tm.cues.ReadabilityMetricsWrapper;

import com.ipeirotis.readability.enums.MetricType;

public class CueGunningFogIndex extends Cue {
  public CueGunningFogIndex() {
    super("GunningFogIndex", "Gunning Fog Index", "The index estimates the years of formal education needed to understand the text on a first reading", CueValueType.Real);
  }

  @Override
  public void extractCue(Observationable observation) {
    DetectorException.throwIfNull(observation, "observation");
    String rawText = observation.getObservationRawText();

    ReadabilityMetricsWrapper readability = ReadabilityMetricsWrapper.getInstance();
    readability.setText(rawText);

    setValue(readability.getReadabilityMetric(MetricType.GUNNING_FOG));
  }

  @Override
  public String toString() {
    return Utilites.serializeDouble(getValue());
  }
}
