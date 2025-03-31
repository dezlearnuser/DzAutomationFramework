package com.dz.config;

import java.util.Properties;
import com.dz.utils.PropertyManager;

public class Config {

  public static Properties props = PropertyManager.getProps();

  public static String getTestEmail() {
    return props.getProperty("test.email", "a360betaworkflowtest");
  }

  public static int getConnectionTimeout() {
    return Integer.parseInt(props.getProperty("test.http.timeout.connection", "3000"));
  }

  public static int getReadTimeout() {
    return Integer.parseInt(props.getProperty("test.http.timeout.read", "35000"));
  }

  public static boolean ignoreCertsEnabled() {
    return Boolean.parseBoolean(props.getProperty("test.ignorecerts", "true"));
  }
}
