/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.common.logger;

public class Logger {
  public static void Log(LogLevel level, String format, Object... args) {
    String message = String.format(format, args);
    logable.write(message);
  }
  private static Logable logable;

  static {
    logable = new ConsoleLog();
  }
}