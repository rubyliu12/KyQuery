package app.util.eagle;

import app.util.ConfigsCache;
import app.util.tools.ConfigUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import redis.clients.jedis.Jedis;

/**
 * Created by Yp on 2017/5/16.
 */
public class HttpClient {

  private static ConfigUtil configUtil = ConfigUtil.getInstance();
  private static Jedis jedis = ConfigsCache.INSTANCE.init();

  /**
   * 登录 login:API登陆样例. <br/>
   *
   * @author Administrator
   * @since JDK 1.8
   */
  public static String fetchToken() {
    if (jedis.isConnected() && Objects.isNull(jedis.get("yyx_login_token"))) {
      Map<String, String> params = new HashMap<>();

      params.put("username", configUtil.getValue("yyx_username"));
      params.put("password", configUtil.getValue("yyx_password"));
      String url = configUtil.getValue("yyx_login_uri");
      String sign = getSingnature(params, null);
      String result = HttpRequest.doPost(url, JSONObject.toJSONString(params), sign, null, true);

      JSONObject resultJson = JSONObject.parseObject(result);
      if (!resultJson.isEmpty() && EagleResponseCode.SUCCESS.getResponseCode()
          .equals(resultJson.getString(EagleResponseParameter.RESCODE.getResponseParameter()))) {
        jedis.set("yyx_login_token",
            resultJson.getString(EagleResponseParameter.TOKEN.getResponseParameter()));
        jedis.expire("yyx_login_token", 20);
      }
    }

    return jedis.get("yyx_login_token");
  }

  /**
   * 银行流水
   */
  public static JSONObject fetchBankCardFlows(String name, String cardNo,
      String mobile, String idNo, String email) {
    Map<String, String> paramsr = new TreeMap<>();//使用TreeMap用于获取有序参数
    String transactionId = "KYMC" + UUID.randomUUID().toString().replace("-", "");
    String getToken = fetchToken();
    paramsr.put("name", name);
    paramsr.put("cardNo", cardNo);
    paramsr.put("mobile", mobile);
    paramsr.put("idNo", idNo);
    paramsr.put("email", email);
    paramsr.put("transactionId", transactionId);

    String sign = getSingnature(paramsr, getToken);
    String url = configUtil.getValue("yyx_bankflows_uri");
    String result = HttpRequest
        .doPost(url, JSONObject.toJSONString(paramsr), sign, getToken, true);
    JSONObject json = JSONObject.parseObject(result);
    if (null != json && "0003".equals(json.getString("resCode"))) {
      jedis.del("yyx_login_token");
      fetchBankCardFlows(name, cardNo, mobile, idNo, email);
    }
    return json;
//    if (null != json && "0000".equals(json.getString("resCode"))) {
//      return json;
//    }
//    return fetchBankCardFlows(name, cardNo, mobile, idNo, email);
  }

  /**
   * 高级流水查询接口
   */
  public static String fetchAdvancedBankCardFlows(/*高级流水查询参数*/
      AdvancedBankCardFlowsParameter bankCardFlowsParameter) {

    Map<String, String> queryParameter = new TreeMap<>();
    queryParameter.putAll(bankCardFlowsParameter.toMap());
    //交易流水号，随机生成
    String transactionId = "KYMC" + UUID.randomUUID().toString().replace("-", "");
    queryParameter.put("transactionId", transactionId);
    String token = fetchToken();

    String sign = getSingnature(queryParameter, token);
    String advancedBankCardFlowsUri = configUtil.getValue("yyx_advanced_bankcard_flows_uri");

    String result = HttpRequest
        .doPost(advancedBankCardFlowsUri, JSONObject.toJSONString(queryParameter), sign, token, /*isHttps*/
            true);

//    JSONObject resultJson = JSONObject.parseObject(result);
    //token超时重新获取token
    /*if (EagleResponseCode.TOKENTIMEOUT.getResponseCode()
        .equals(resultJson.getString(EagleResponseParameter.RESCODE.getResponseParameter()))) {
      String newToken = fetchToken();
      result = HttpRequest
          .doPost(advancedBankCardFlowsUri, JSONObject.toJSONString(queryParameter), sign, newToken, *//*isHttps*//*
              true);
    }*/
    return result;
  }

  /**
   * 户籍信息
   */
  public static JSONObject fetchDtpersonbasicinfo(String name,
      String idNo) {
    Map<String, String> paramsr = new TreeMap<>();//使用TreeMap用于获取有序参数
    String transactionId = "KYMC" + UUID.randomUUID().toString().replace("-", "");
    String url = configUtil.getValue("yyx_dtpquery_uri");
    String getToken = fetchToken();
    paramsr.put("name", name);
    paramsr.put("idNo", idNo);
    paramsr.put("transactionId", transactionId);

    String sign = getSingnature(paramsr, getToken);

    String result = HttpRequest
        .doPost(url, JSONObject.toJSONString(paramsr), sign, getToken, true);
    JSONObject json = JSONObject.parseObject(result);

    return json;
//    if (null != json && "0000".equals(json.getString("resCode"))) {
//      return json;
//    }
//    return fetchDtpersonbasicinfo(name, idNo);
  }

  /**
   * 通过该接口获取被查询用户身份证号与姓名一致性，以及肖像核查。
   * @param name
   * @param idNo
   * @return
   */
  public static JSONObject idVerify(String name, String idNo) {
    Map<String, String> paramsr = new TreeMap<>();//使用TreeMap用于获取有序参数
    String transactionId = "KYMC" + UUID.randomUUID().toString().replace("-", "");
    String getToken = fetchToken();
    paramsr.put("name", name);
    paramsr.put("idNo", idNo);
    paramsr.put("transactionId", transactionId);

    String sign = getSingnature(paramsr, getToken);
    String url = configUtil.getValue("yyx_idverify_uri");
    String result = HttpRequest
        .doPost(url, JSONObject.toJSONString(paramsr), sign, getToken, true);
    JSONObject json = JSONObject.parseObject(result);
    if (null != json && "0003".equals(json.getString("resCode"))) {
      jedis.del("yyx_login_token");
      idVerify(name, idNo);
    }
    return json;
  }

  /**
   * 获取参数签名 getSingnature:拼接参数生成数据签名. <br/>
   *
   * @since JDK 1.8
   */
  public static String getSingnature(Map<String, String> params, String token) {
    Map<String, String> map = new TreeMap<>();
    StringBuilder signStr = new StringBuilder();

    if (!params.isEmpty()) {
      map.putAll(params);
      map.forEach((k, v) -> signStr.append(k).append(v));
    }
    if (!Strings.isNullOrEmpty(token)) {
      signStr.append(token);
    }
    return SignatureData.signContent(signStr.toString());
  }

}
