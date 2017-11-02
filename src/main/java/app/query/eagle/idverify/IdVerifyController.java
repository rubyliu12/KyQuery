package app.query.eagle.idverify;

import static app.Application.dtpInfoDao;
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
 * 身份验证Controller
 */
public class IdVerifyController {

  public static Route indexIdVerify = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();
    model.put("idVerifyParams",
        ImmutableMap.builder().put("name", "").put("idno", "").build());
    if (clientAcceptsHtml(request)) {
      return ViewUtil.render(request, model, Template.ID_VERIFY_INDEX);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route idVerify = (Request request, Response response) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("idVerifyParams", ImmutableMap.builder().put("name", getQueryName(request)).put("idno", getQueryIdNo(request)).build());

    if (!Verification.is18ByteIdCard(getQueryIdNo(request))) {
      model.put("idCardQueryFailed", true);
      return ViewUtil.render(request, model, Template.ID_VERIFY_INDEX);
    }

    JSONObject jsonObject = HttpClient.idVerify(getQueryName(request),
        getQueryIdNo(request));

    if (Objects.isNull(jsonObject)) {
      model.put("errorResponse", "请联系开发人员");
      return ViewUtil.render(request, model, Template.ID_VERIFY_INDEX);
    }
    Map<String, Object> rsMap = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
    if ("0000".equals(rsMap.get("resCode").toString())) {

      model.put("verifyResult", jsonObject.getJSONObject("data"));
      StorageInfo
          .storageQueryInfo(getSessionCurrentUser(request), getQueryName(request), getQueryIdNo(request), jsonObject.getString("data"));
    } else {
      model.put("errorResponse", rsMap.get("resMsg").toString());
    }
    return ViewUtil.render(request, model, Template.ID_VERIFY_INDEX);
  };
}
