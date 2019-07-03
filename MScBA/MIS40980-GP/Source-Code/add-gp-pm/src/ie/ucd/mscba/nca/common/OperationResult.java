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

public class OperationResult {

  public OperationResult(boolean success, String message, Exception innerException, Object runtimeObject) {
    this.success = success;
    this.message = message;
    this.innerException = innerException;
    this.runtimeObject = runtimeObject;
  }

  public OperationResult(boolean success, String message, Exception innerException) {
    this(success, message, innerException, DefaultRuntimeObject);
  }

  public OperationResult(boolean success, String message) {
    this(success, message, DefaultInnerException, DefaultRuntimeObject);
  }

  public OperationResult() {
    this(DefaultSuccess, DefaultMessage, DefaultInnerException, DefaultRuntimeObject);
  }

  public OperationResult(Object runtimeObject) {
    reset();

    this.runtimeObject = runtimeObject;
  }

  void reset() {
    success = DefaultSuccess;
    message = DefaultMessage;
    innerException = DefaultInnerException;
    runtimeObject = DefaultRuntimeObject;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Exception getInnerException() {
    return innerException;
  }

  public void setInnerException(Exception innerException) {
    this.innerException = innerException;
  }

  public Object getRuntimeObject() {
    return runtimeObject;
  }

  public void setRuntimeObject(Object runtimeObject) {
    this.runtimeObject = runtimeObject;
  }

  private static boolean DefaultSuccess = true;
  private static String DefaultMessage = "";
  private static Exception DefaultInnerException = new Exception();
  private static Object DefaultRuntimeObject = new Object();

  private boolean success;
  private String message;
  private Exception innerException;
  private Object runtimeObject;
}
