package com.dz.customExceptions;

/**
 * @author Ratnesh Prakash.
 * Custom exception to report exception while initializing appium driver
 */
public class SampleCustomException extends RuntimeException {

  public SampleCustomException(String errorMessage) {
    super(errorMessage);
  }

  SampleCustomException(String message, Throwable cause) {
    super(message, cause);
  }

  SampleCustomException(Throwable cause) {
    super(cause);
  }

}
