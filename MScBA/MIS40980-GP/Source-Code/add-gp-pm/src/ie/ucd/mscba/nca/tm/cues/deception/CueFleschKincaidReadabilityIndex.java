package ie.ucd.mscba.nca.tm.cues.deception;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.ds.Observationable;
import ie.ucd.mscba.nca.tm.cues.Cue;
import ie.ucd.mscba.nca.tm.cues.CueValueType;
import ie.ucd.mscba.nca.tm.cues.ReadabilityMetricsWrapper;

import com.ipeirotis.readability.enums.MetricType;

public class CueFleschKincaidReadabilityIndex extends Cue {

  public CueFleschKincaidReadabilityIndex() {
    super("FleschKincaidReadabilityIndex", "Flesch-Kincaid Readability Index", " A readability tests designed to indicate how difficult a reading passage in English is to understand", CueValueType.Real);
  }

  @Override
  public void extractCue(Observationable observation) {
    DetectorException.throwIfNull(observation, "observation");
    String rawText = observation.getObservationRawText();

    ReadabilityMetricsWrapper readability = ReadabilityMetricsWrapper.getInstance();
    readability.setText(rawText);

    setValue(readability.getReadabilityMetric(MetricType.FLESCH_KINCAID));
  }

  @Override
  public String toString() {
    return Utilites.serializeDouble(getValue());
  }
}
