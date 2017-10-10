package app.query.baiqishi;

import app.util.tools.ConfigUtil;
import app.util.tools.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

public class BQSIVSQuery {
  private static ConfigUtil configUtil = ConfigUtil.getInstance();

  /**
   * IVS查询
   * @param queryInfo net.sf.JSONObject
   * @return
   */
  public static String ivsQuery(JSONObject queryInfo) {
    Invoker.init();

    Map<String, Object> params = new HashMap<>();
    params.put("verifyKey", configUtil.getValue("verifyKey"));
    params.put("partnerId", configUtil.getValue("partnerId"));

    //需要独立配置，会发生变化    管理中心->应用管理
    params.put("appId", configUtil.getValue("appId"));

    //业务参数
    params.put("eventType", "blacklist"); //  跟着场景 触发

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    params.put("occurTime ", sdf.format(new Date()));
    params.put("mobile", queryInfo.get("mobile"));
    params.put("name", queryInfo.get("name"));
    params.put("certNo", queryInfo.get("certNo"));
    String result = null;
    try {
      result = Invoker.invoke(params, configUtil.getValue("ivs_url"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * IVS查询
   * @param bp BQSIVSQueryParameters Object
   * @return
   */
  public static String ivsQuery(BQSIVSQueryParameters bp) {
    String result = null;
    //BQSIVSQueryParameters Object to Map
    Map<String, Object> query = JsonUtil.toMap(bp);
    try {
      result = Invoker.invoke(query, configUtil.getValue("ivs_url"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(result);
    return result;
  }
}
