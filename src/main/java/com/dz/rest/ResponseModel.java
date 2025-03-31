package com.dz.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResponseModel {
  private final int code;
  private final Exception exception;
  private final String body;
  private Map<String, List<String>> responseHeaders = new HashMap<>();

  private ResponseModel(Builder builder) {
    this.code = builder.code;
    this.exception = builder.exception;
    this.body = builder.body;
    this.responseHeaders = builder.responseHeaders;
  }

  public int getCode() {
    return code;
  }

  public Exception getException() {
    return exception;
  }

  public String getBody() {
    return body;
  }

  public Map<String, List<String>> getResponseHeaders() {
    return responseHeaders;
  }

  @Override
  public String toString() {
    return "ResponseModel{" + "code=" + code + ", exception=" + exception + ", body='" + body + '\'' + ", responseHeaders=" + responseHeaders + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseModel that = (ResponseModel) o;
    return code == that.code && Objects.equals(exception, that.exception) && Objects.equals(body, that.body) && Objects.equals(responseHeaders,
                                                                                                                               that.responseHeaders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, exception, body, responseHeaders);
  }

  public static class Builder {
    private int code;
    private Exception exception;
    private String body;
    private Map<String, List<String>> responseHeaders = new HashMap<>();

    // Setter methods for the fields
    public Builder code(int code) {
      this.code = code;
      return this;
    }

    public Builder exception(Exception exception) {
      this.exception = exception;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Builder responseHeaders(Map<String, List<String>> responseHeaders) {
      this.responseHeaders = responseHeaders;
      return this;
    }

    // Build method to create an instance of ResponseModel
    public ResponseModel build() {
      return new ResponseModel(this);
    }
  }
}


