package com.dz.utils;

import java.util.Random;

public class UtilityFunctions {

  public static String generateRandomAlphanumeric(int length) {

    Random RANDOM = new Random();
    final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    if (length <= 0) {
      throw new IllegalArgumentException("Length must be a positive integer.");
    }

    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(randomIndex));
    }
    return sb.toString();
  }
}
