package ie.ucd.mscba.nca.tm.cues.deception;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.ds.Observationable;
import ie.ucd.mscba.nca.tm.cues.Cue;
import ie.ucd.mscba.nca.tm.cues.CueValueType;
import ie.ucd.mscba.nca.tm.cues.ReadabilityMetricsWrapper;

import com.ipeirotis.readability.enums.MetricType;

public class CueNumberOfWords extends Cue {
  public CueNumberOfWords() {
    super("NumberOfWords", "Number Of Words", "Total number of words for a given observation", CueValueType.Integer);
  }

  @Override
  public void extractCue(Observationable observation) {
    DetectorException.throwIfNull(observation, "observation");
    String rawText = observation.getObservationRawText();

    ReadabilityMetricsWrapper readability = ReadabilityMetricsWrapper.getInstance();
    readability.setText(rawText);

    setValue(readability.getReadabilityMetric(MetricType.WORDS));
  }

  @Override
  public String toString() {
    return Utilites.serializeInteger(getValue());
  }
}
