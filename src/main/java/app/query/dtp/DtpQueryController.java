package app.query.dtp;

import static app.Application.dtpInfoDao;
import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getQueryIdNo;
import static app.util.RequestUtil.getQueryName;
import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import app.util.Path;
import app.util.Path.Template;
import app.util.ViewUtil;
import app.util.database.StorageInfo;
import app.util.eagle.HttpClient;
import app.util.tools.Verification;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Yp on 2017/5/15.
 */
public class DtpQueryController {

  public static Route serverQueryPage = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    if (clientAcceptsHtml(request)) {
      HashMap<String, Object> model = new HashMap<>();
      return ViewUtil.render(request, model, Path.Template.DTP_QUERY);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route dtpQuery = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();
    if (!Verification.is18ByteIdCard(getQueryIdNo(request))) {
      model.put("dtpIdCardQueryFailed", true);
      return ViewUtil.render(request, model, Template.DTP_QUERY);
    }

    JSONObject jsonObject = HttpClient
        .fetchDtpersonbasicinfo(getQueryName(request),
            getQueryIdNo(request));
    if (Objects.isNull(jsonObject)) {
      model.put("errorResponse", "请联系开发人员");
      return ViewUtil.render(request, model, Template.DTP_QUERY);
    }
    Map<String, Object> rsMap = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
    if ("0000".equals(rsMap.get("resCode").toString())) {

      model.put("dtpInfo",
          dtpInfoDao.toDtpInfo(getQueryName(request), getQueryIdNo(request), jsonObject.getJSONObject("data")));
      StorageInfo.storageQueryInfo(getSessionCurrentUser(request), getQueryName(request), getQueryIdNo(request), jsonObject.getString("data"));
    } else {
      model.put("errorResponse", rsMap.get("resMsg").toString());
    }
    return ViewUtil.render(request, model, Template.DTP_QUERY);
  };

}
