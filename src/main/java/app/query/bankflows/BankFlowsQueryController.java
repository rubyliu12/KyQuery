package app.query.bankflows;

import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getQueryCardNo;
import static app.util.RequestUtil.getQueryEmail;
import static app.util.RequestUtil.getQueryIdNo;
import static app.util.RequestUtil.getQueryMobile;
import static app.util.RequestUtil.getQueryName;
import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import app.util.Path.Template;
import app.util.ViewUtil;
import app.util.database.StorageInfo;
import app.util.eagle.HttpClient;
import app.util.tools.Verification;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Yp on 2017/5/19.
 */
public class BankFlowsQueryController {

  public static Route serverBankFlows = (Request request, Response response) -> {
    //    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();
    model.put("bfQueryParams",
        ImmutableMap.builder().put("name", "").put("cardno", "").put("idno", "").put("mobile", "")
            .put("email", "").build());
    if (clientAcceptsHtml(request)) {
      return ViewUtil.render(request, model, Template.BANK_QUERY);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route bankflowsQuery = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    BankflowsInfoDao bankflowsInfoDao = new BankflowsInfoDao();
    Map<String, Object> model = new HashMap<>();
    model.put("bfQueryParams", ImmutableMap.builder().put("name", getQueryName(request))
        .put("cardno", getQueryCardNo(request)).put("idno", getQueryIdNo(request))
        .put("mobile", getQueryMobile(request))
        .put("email", getQueryEmail(request)).build());
    if (!Verification.is18ByteIdCard(getQueryIdNo(request))) {
      model.put("bankflowsIdCardQueryFailed", true);
      return ViewUtil.render(request, model, Template.BANK_QUERY);
    }

    JSONObject jsonObject = HttpClient
        .fetchBankCardFlows(getQueryName(request), getQueryCardNo(request), getQueryMobile(request),
            getQueryIdNo(request), getQueryEmail(request));
    if (Objects.isNull(jsonObject)) {
      model.put("errorResponse", "请联系开发人员");
      return ViewUtil.render(request, model, Template.BANK_QUERY);
    }
    Map<String, Object> rsMap = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
    if ("0000".equals(rsMap.get("resCode").toString())) {

      model.put("bankflowsList",
          bankflowsInfoDao.toBankflowInfo(jsonObject.getJSONArray("data")));

    } else {
      model.put("errorResponse", rsMap.get("resMsg").toString());
    }
    String info = jsonObject.getString("data").isEmpty() ? "" : jsonObject.getString("data");
    StorageInfo
        .storageBankflows(getSessionCurrentUser(request), getQueryName(request),
            getQueryIdNo(request), info);
    return ViewUtil.render(request, model, Template.BANK_QUERY);
  };
}
