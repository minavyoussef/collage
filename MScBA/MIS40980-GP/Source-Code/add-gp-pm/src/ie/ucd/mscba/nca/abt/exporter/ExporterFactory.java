/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.abt.exporter;

import java.util.Hashtable;

public final class ExporterFactory {
  private ExporterFactory() {}

  public static Exportable getExportable(ExportableTypes types) {
    return mapper.get(types);
  }

  private static Hashtable<ExportableTypes, Exportable> mapper;
  static {
    mapper = new Hashtable<>();
    mapper.put(ExportableTypes.csv, new CSVExporter());
  }
}
