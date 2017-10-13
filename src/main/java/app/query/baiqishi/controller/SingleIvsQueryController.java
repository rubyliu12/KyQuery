package app.query.baiqishi.controller;

import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getQueryBankCardNo;
import static app.util.RequestUtil.getQueryCertdNo;
import static app.util.RequestUtil.getQueryEmail;
import static app.util.RequestUtil.getQueryMobile;
import static app.util.RequestUtil.getQueryName;

import app.login.LoginController;
import app.query.baiqishi.BqsIvsQuery;
import app.query.baiqishi.BqsIvsQueryParameters;
import app.util.Path.Template;
import app.util.ViewUtil;
import app.util.tools.Verification;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.sf.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class SingleIvsQueryController {

  public static Route serverIvsQuery = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();
    model.put("ivsQueryParams",
        ImmutableMap.builder().put("name", "").put("certNo", "").put("bankCardNo", "")
            .put("mobile", "")
            .put("email", "").build());
    if (clientAcceptsHtml(request)) {
      return ViewUtil.render(request, model, Template.BQS_IVS_SINGLE);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route singleIvsQuery = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();
    model.put("ivsQueryParams", ImmutableMap.builder().put("name", getQueryName(request))
        .put("certNo", getQueryCertdNo(request)).put("bankCardNo", getQueryBankCardNo(request))
        .put("mobile", getQueryMobile(request))
        .put("email", getQueryEmail(request)).build());

    if (!Verification.is18ByteIdCard(getQueryCertdNo(request))) {
      model.put("bankflowsIdCardQueryFailed", true);
      return ViewUtil.render(request, model, Template.BQS_IVS_SINGLE);
    }
    BqsIvsQueryParameters bp = BqsIvsQueryParameters.builder().name(getQueryName(request))
        .certNo(getQueryCertdNo(request)).mobile(getQueryMobile(request))
        .bankCardNo(getQueryBankCardNo(request)).email(getQueryEmail(request)).build();

    String result = BqsIvsQuery.ivsQuery(bp);

    JSONObject resultJs = JSONObject.fromObject(result);

    if (Objects.isNull(resultJs) || !("BQS000".equals(resultJs.get("resultCode")))) {
      model.put("errorResponse", "请联系开发人员");
      return ViewUtil.render(request, model, Template.BQS_IVS_SINGLE);
    }

    model.put("ivsResult", resultJs);
    return ViewUtil.render(request, model, Template.BQS_IVS_SINGLE);
  };
}
