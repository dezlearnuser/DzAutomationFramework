package com.dz.rest;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomLoggingFilter implements Filter {

  // Set of headers that need to be masked
  private static final Set<String> SENSITIVE_HEADERS = new HashSet<>();

  static {
    SENSITIVE_HEADERS.add("Authorization");
    SENSITIVE_HEADERS.add("Proxy-Authorization");
    SENSITIVE_HEADERS.add("Cookie");
  }

  @Override
  public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
    // Log request details
    System.out.println("Request URI: " + requestSpec.getURI());
    System.out.println("Request Method: " + requestSpec.getMethod());
    System.out.println("Request Headers: " + maskSensitiveHeaders(requestSpec.getHeaders()));
    System.out.println("Request Body: " + requestSpec.getBody());

    // Proceed with the request and get the response
    Response response = ctx.next(requestSpec, responseSpec);

    // Log response details
    System.out.println("Response Status Code: " + response.getStatusCode());
    System.out.println("Response Headers: " + response.getHeaders());
    System.out.println("Response Body: " + response.getBody().asString());

    return response;
  }

  private Headers maskSensitiveHeaders(Headers headers) {
    List<Header> maskedHeadersList = new ArrayList<>();
    for (Header header : headers) {
      if (SENSITIVE_HEADERS.contains(header.getName())) {
        maskedHeadersList.add(new Header(header.getName(), "******"));
      } else {
        maskedHeadersList.add(header);
      }
    }
    return new Headers(maskedHeadersList);
  }
}


