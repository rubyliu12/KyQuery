package app.util.eagle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLContext;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yp on 2017/5/16.
 */
public class HttpRequest {

  private static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);
  private static final int TIMEOUT = 80000;
  private static final String DEFAULT_CHARSET = "UTF-8";
  private static CloseableHttpClient client = getHttpClientInstance();
  private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
      .setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT)
      .build();

  public static String doGet(String url, boolean isHttps) {

    try {
      HttpGet get = new HttpGet(url);
      get.setConfig(REQUEST_CONFIG);
      LOGGER.debug("request: {}", url);
      String res = execute(get);
      LOGGER.debug("response : {}", res);
      return res;
    } catch (Exception e) {
      LOGGER.error(" do get request fail: {}", e.getMessage());
      return "{\"request_fail\":\"" + e.getMessage() + "\"}";
    }
  }

  private static String inputStreamToString(InputStream is) {
    if (is == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is, Consts.UTF_8.name()));
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String result = sb.toString();
    return result;

  }

  private synchronized static CloseableHttpClient getHttpClientInstance() {
    FileInputStream instream = null;
    try {
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      TrustStrategy anyTrustStrategy = new TrustStrategy() {

        @Override
        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
          return true;
        }
      };
      SSLContext sslcontext = SSLContexts.custom()
          .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
      // Allow TLSv1 protocol only
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
          new String[]{"TLSv1.2"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } finally {
      try {
        if (instream != null) {
          instream.close();
        }
      } catch (Exception ignore) {
        ignore.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 生成请求的url
   *
   * @param url 不带参数的url字符串
   * @param params 参数
   * @return URI， 请求的uri对象
   */
  private static URI generateURL(String url, Map<String, String> params) {
    URI uri = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(url);
      if (null != params) {
        for (Entry<String, String> entry : params.entrySet()) {
          uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
      }
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return uri;
  }

  public static String doGet(String url, Map<String, String> params, boolean isHttps) {
    try {
      URI uri = generateURL(url, params);
      LOGGER.debug("请求:" + uri.toString());
      return doGet(uri.toString(), isHttps);
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"request_fail\":\"" + e.getMessage() + "\"}";
    }
  }

  public static String doPost(String url, String requestBody, boolean isHttps) {
    URI uri = generateURL(url, null);
    HttpPost post = new HttpPost(uri);
    post.setConfig(REQUEST_CONFIG);

    if (null != requestBody) {
      LOGGER.debug("request: {} params :{}", url, requestBody);
      HttpEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
      post.setEntity(entity);
    }

    String res = execute(post);

    LOGGER.debug("request: " + url + " response :" + res);
    return res;
  }

  /**
   * post请求
   *
   * @param url 请求url url后参数 request body对象 (可为 null)
   * @return 请求结果
   */
  public static String doPost(String url, String requestBody, String signature, String token,
      boolean isHttps) {

    URI uri = generateURL(url, null);
    HttpPost post = new HttpPost(uri);
    post.addHeader("token", token);
    post.addHeader("signature", signature);
    post.setConfig(REQUEST_CONFIG);
    if (null != requestBody) {
      LOGGER.debug("request: {} params :{}", url, requestBody);
      HttpEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
      post.setEntity(entity);
    }
    String res = execute(post);

    LOGGER.debug("request: " + url + " response :" + res);
    return res;
  }

  private static String execute(HttpUriRequest request) {
    String responseStr = null;
    CloseableHttpResponse httpResponse = null;
    try {
      httpResponse = client.execute(request);
      responseStr = EntityUtils.toString(httpResponse.getEntity(), DEFAULT_CHARSET);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (null != httpResponse) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    }
    return responseStr;
  }

  /**
   * 发起请求，关闭资源
   *
   * @param request （HttpPost 或 HttpGet）
   * @return response 请求返回值
   */
  private static String execute2(HttpUriRequest request) {
    String responseStr = null;
    CloseableHttpResponse httpResponse = null;
    try {
      httpResponse = client.execute(request);
      responseStr = EntityUtils.toString(httpResponse.getEntity(), DEFAULT_CHARSET);
    } catch (IOException e) {
      LOGGER.error("Request {}  IOEXception ERROR ,detail is {},{}", request.getURI().toString(),
          e.getMessage(), e);
      return "{\"request_failed\":\"" + e.getMessage() + "\"}";
    } finally {
      if (null != httpResponse) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          LOGGER.error("close http response ERROR {}", e.getMessage());
        }
      }
      if (client != null) {
        try {
          client.close();
        } catch (IOException e) {
          LOGGER.error("close Http client ERROR {}", e.getMessage());
        }
      }
    }
    return responseStr;
  }
}
