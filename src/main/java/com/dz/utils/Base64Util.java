package com.dz.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Util {

  private static final String BASE64_ENCODED_PATTERN_STRING = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
  private static final Pattern BASE64_ENCODED_PATTERN = Pattern.compile(BASE64_ENCODED_PATTERN_STRING);

  public static String encodeBase64UrlSafe(String text) {
    return Base64.encodeBase64URLSafeString(text.getBytes());
  }

  public static String decodeBase64UrlSafe(String text) {
    byte[] bytes = Base64.decodeBase64(text);
    return new String(bytes);
  }

  public static boolean isBase64Encoded(String str) {
    Matcher matcher = BASE64_ENCODED_PATTERN.matcher(str);
    return matcher.find();
  }
}
