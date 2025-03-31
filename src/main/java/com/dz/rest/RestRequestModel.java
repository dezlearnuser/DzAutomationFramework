package com.dz.rest;

import io.restassured.http.Method;

import java.util.HashMap;
import java.util.Map;

public class RestRequestModel {
  private final String uri;
  private final Method httpMethod;
  private final Object body;
  private final Map<String, String> headers;
  private final Map<String, String> queryParams;
  private final Map<String, String> pathParams;
  private final boolean urlEncodingEnabled;

  private RestRequestModel(Builder builder) {
    this.uri = builder.uri;
    this.httpMethod = builder.httpMethod;
    this.body = builder.body;
    this.headers = builder.headers;
    this.queryParams = builder.queryParams;
    this.pathParams = builder.pathParams;
    this.urlEncodingEnabled = builder.urlEncodingEnabled;
  }

  public String getUri() {
    return uri;
  }

  public Method getHttpMethod() {
    return httpMethod;
  }

  public Object getBody() {
    return body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public Map<String, String> getPathParams() {
    return pathParams;
  }

  public boolean isUrlEncodingEnabled() {
    return urlEncodingEnabled;
  }

  public static class Builder {
    private String uri;
    private Method httpMethod;
    private Object body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> pathParams = new HashMap<>();
    private boolean urlEncodingEnabled = true;

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder httpMethod(Method httpMethod) {
      this.httpMethod = httpMethod;
      return this;
    }

    public Builder body(Object body) {
      this.body = body;
      return this;
    }

    public Builder headers(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder queryParams(Map<String, String> queryParams) {
      this.queryParams = queryParams;
      return this;
    }

    public Builder pathParams(Map<String, String> pathParams) {
      this.pathParams = pathParams;
      return this;
    }

    public Builder urlEncodingEnabled(boolean urlEncodingEnabled) {
      this.urlEncodingEnabled = urlEncodingEnabled;
      return this;
    }

    public RestRequestModel build() {
      return new RestRequestModel(this);
    }
  }
}
