package com.dz.rest;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestCallHandler {

//  private static RestCallHandler restCallHandler = null;

//  private RestCallHandler() {
//    // basic Rest Assured config settings
//  }

//  public static RestCallHandler instance() {
//    if (restCallHandler == null) {
//      restCallHandler = new RestCallHandler();
//    }
//    return restCallHandler;
//  }

  public Map<String, String> getBasicHeaderMap() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    return headers;
  }

  public ResponseModel sendRequest(RequestSpecification request, Method httpMethod) {
    try {
      Response response = given(request).request(httpMethod).then().extract().response();
      return getHttpResponse(response);
    } catch (Exception e) {
      return new ResponseModel.Builder().exception(e).build();
    }
  }

  private RequestSpecification basicRequest() {
    // Create a new RequestSpecBuilder using the received RequestSpecification
    RequestSpecBuilder specBuilder = new RequestSpecBuilder();

    // Add filters to the RequestSpecBuilder
    specBuilder.addFilter(new RequestLoggingFilter(LogDetail.URI));
    specBuilder.addFilter(new ResponseLoggingFilter(LogDetail.BODY));

    // Build and return the modified RequestSpecification
    return specBuilder.build();
  }

  private ResponseModel getHttpResponse(Response response) {
    final Map<String, List<String>> responseHeaders = response.getHeaders().asList().stream().collect(
        Collectors.groupingBy(Header::getName, Collectors.mapping(Header::getValue, Collectors.toList())));

    return new ResponseModel.Builder().code(response.getStatusCode()).responseHeaders(responseHeaders).body(response.body().asString()).build();
  }

  public int getResonseCode(ResponseModel responseModel) {
    return responseModel.getCode();
  }

}
