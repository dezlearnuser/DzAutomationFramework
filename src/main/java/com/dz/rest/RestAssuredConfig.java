package com.dz.rest;


import com.dz.config.Config;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.SSLConfig;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.SyncBasicHttpParams;
import org.slf4j.Logger;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RestAssuredConfig {
  private static final Logger logger = com.dz.utils.LogUtil.log();
  static PoolingClientConnectionManager cm = new PoolingClientConnectionManager();

  static {
    logger.info("--------- Setting up RestAssured Configuration ---------");
    cm.setMaxTotal(500);
    cm.setDefaultMaxPerRoute(100);

    if (Config.ignoreCertsEnabled()) {
      SSLConfig sslConfig = RestAssured.config().getSSLConfig().relaxedHTTPSValidation().allowAllHostnames();
      RestAssured.config = RestAssured.config().sslConfig(sslConfig);
      RestAssured.config = RestAssured.config().sslConfig(sslConfig);

      ignoreCertsForHttpUrlConn();
    }
    applyHttpConnectionTimeouts();
    RestAssured.filters(new CustomLoggingFilter());
  }

  private static void applyHttpConnectionTimeouts() {
    final Integer socketTimeout = Config.getConnectionTimeout();
    final Integer readTimeout = Config.getReadTimeout();
    HttpClientConfig.HttpClientFactory customHttpClientFactory = new HttpClientConfig.HttpClientFactory() {
      @Override
      public HttpClient createHttpClient() {
        SyncBasicHttpParams params = new SyncBasicHttpParams();
        DefaultHttpClient.setDefaultHttpParams(params);
        params.setIntParameter("http.connection.timeout", socketTimeout); //9 sec for connection
        params.setIntParameter("http.socket.timeout", readTimeout); //35 sec for read.
        /* System.out.println(
                        "Setting timeouts :: socket timeout[" + socketTimeout / 1000 + "] sec and read timeout[" +
                                readTimeout / 1000 + "] sec");*/
        return new DefaultHttpClient(cm, params);
      }
    };
    HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig();
    httpClientConfig = httpClientConfig.httpClientFactory(customHttpClientFactory);
    ConnectionConfig connectionConfig = ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponseAfter(
        new ConnectionConfig.CloseIdleConnectionConfig(Long.getLong("test.http.timeout.read", 35000), TimeUnit.MILLISECONDS));
    RestAssured.config = RestAssured.config().httpClient(httpClientConfig).connectionConfig(connectionConfig);
    System.setProperty("sun.net.client.defaultConnectTimeout", socketTimeout.toString());
    System.setProperty("sun.net.client.defaultReadTimeout", readTimeout.toString());
    System.out.println("=-++++++++++ Not closing idle connections");
  }

  private static void ignoreCertsForHttpUrlConn() {
    TrustManager trm = new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {

      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }
    };

    SSLContext sc = null;
    try {
      sc = SSLContext.getInstance("SSL");
      sc.init(null, new TrustManager[] {trm}, null);
    } catch (NoSuchAlgorithmException e) {
      logger.error("Failed overriding JDK Certs validation", e);
    } catch (KeyManagementException e) {
      logger.error("Failed overriding JDK Certs validation", e);
    }
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
      public boolean verify(String string, SSLSession ssls) {
        return true;
      }
    });

  }

}
