/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.tm.cues;

import ie.ucd.mscba.nca.common.DetectorException;

import java.util.HashMap;
import java.util.Map;

import com.ipeirotis.readability.engine.Readability;
import com.ipeirotis.readability.enums.MetricType;

public class ReadabilityMetricsWrapper {
  private ReadabilityMetricsWrapper() {}

  public synchronized static ReadabilityMetricsWrapper getInstance() {
    return (instance == null) ? instance = new ReadabilityMetricsWrapper() : instance;
  }

  public static Map<MetricType, Double> getReadabilityIndex(String text) {
    Readability r = new Readability(text);

    Map<MetricType, Double> result = new HashMap<>();
    for (MetricType metricType : MetricType.values()) {
      result.put(metricType, r.getMetric(metricType));
    }
    return result;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    DetectorException.throwIfNullOrEmpty(text, "text");

    if (this.text == null || !this.text.equalsIgnoreCase(text)) {
      this.text = text;
      cachedReadabilityMetrics = getReadabilityIndex(text);
    }
  }

  public double getReadabilityMetric(MetricType metric) {
    if (cachedReadabilityMetrics == null) {
      return 0.0;
    }

    return cachedReadabilityMetrics.get(metric);
  }

  private static ReadabilityMetricsWrapper instance;
  private String text;
  private Map<MetricType, Double> cachedReadabilityMetrics;
}
