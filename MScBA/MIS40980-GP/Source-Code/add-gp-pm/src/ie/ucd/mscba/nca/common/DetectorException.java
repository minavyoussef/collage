/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.common;

import java.io.File;

public class DetectorException extends Exception {
  public DetectorException(String message) {
    super(message);
  }

  public DetectorException(String message, OperationResult validationResult) {
    this(message);

    setOperationResult(validationResult);
  }

  public static void throwIfNull(Object obj, String varName) {
    if (obj == null) {
      throw new IllegalArgumentException(String.format("Argument %s is null", varName));
    }
  }

  public static void throwIfNullOrEmpty(String obj, String varName) {
    if (obj == null) {
      throw new IllegalArgumentException(String.format("Argument %s is null", varName));
    } else if (obj.equals("")) {
      throw new IllegalArgumentException(String.format("Argument %s is an empty string", varName));
    }
  }

  public static void throwIfPathNotExists(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IllegalArgumentException(String.format("File %s does not exists", filePath));
    }
  }

  public static void throwIfPathNotDirectory(String dirPath) {
    File dir = new File(dirPath);
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException(String.format("%s is not a directory", dirPath));
    }
  }

  public static void throwIfPathNotFile(String filePath) {
    File dir = new File(filePath);
    if (dir.isDirectory()) {
      throw new IllegalArgumentException(String.format("%s is a directory", filePath));
    }
  }

  public static void throwIfNegative(int number) {
    if (number <= 0) {
      throw new IllegalArgumentException(String.format("Expecting positive number: %d", number));
    }
  }

  public static void throwIfZero(int number) {
    if (number == 0) {
      throw new IllegalArgumentException("Zero is not allowed as value");
    }
  }

  public OperationResult getOperationResult() {
    return operationResult;
  }

  public void setOperationResult(OperationResult operationResult) {
    this.operationResult = operationResult;
  }

  private OperationResult operationResult;
}
