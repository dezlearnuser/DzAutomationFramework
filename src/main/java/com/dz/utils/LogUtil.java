package com.dz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ratnesh Prakash.
 * Returns Slf4Logger instance for logging purpose. The underlying Logging framework used here is Log4j.
 */
public class LogUtil {
  /**
   * Return instance of Log4J initialized with name of the name of the class from which it is called.
   */
  public static Logger log() {
    return LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
  }

}
