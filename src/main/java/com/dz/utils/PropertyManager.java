package com.dz.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ratnesh Prakash.
 * Provides functionalities related to config.properties file
 */
@Component
public class PropertyManager {
  private static Properties props = new Properties();
  private static boolean isLoaded = false;

  private PropertyManager() {
  }

  public static synchronized Properties getProps() {
    if (!isLoaded) {
      loadProperties();
      isLoaded = true;
    }
    return props;
  }

  private static void loadProperties() {
    InputStream inputStream = null;
    String propsFileName = "config.properties";

    try {
      inputStream = PropertyManager.class.getClassLoader().getResourceAsStream(propsFileName);
      props.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
