package com.dz.utils;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Ratnesh Prakash.
 * Contains reusable funtions to work with File System
 */
@Component
public class FileUtils {
  @Autowired
  PropertyManager propertyManager;

  /**
   * Get content of given file as String
   */
  public String getFileAsString(String filePath) {
    InputStream stream = null;
    StringBuilder builder = new StringBuilder();
    try {
      stream = this.getClass().getResourceAsStream(filePath);
      BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
      String inputStr;
      while ((inputStr = streamReader.readLine()) != null) {
        builder.append(inputStr);
      }
    } catch (Exception ex) {
      Assert.fail("Exception while reading file: " + filePath);
    }
    return new String(builder);
  }

  /**
   * Get absolute path of file
   */
  public String getFileAbsolutePath(String fileRelativePath) {
    String absolutePath = "";
    try {
      ClassLoader currentClassLoader = this.getClass().getClassLoader();
      File fileObj = new File(currentClassLoader.getResource(fileRelativePath).getFile());
      absolutePath = fileObj.getAbsolutePath();
    } catch (Exception ex) {
      Assert.fail("Exception while getting absolute path of file: " + ex.getMessage());
    }
    return absolutePath;
  }

}
