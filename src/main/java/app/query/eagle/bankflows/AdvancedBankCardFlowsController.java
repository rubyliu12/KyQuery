package app.query.eagle.bankflows;

import static app.login.LoginController.ensureUserIsLoggedIn;
import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getQueryIdNo;
import static app.util.RequestUtil.getQueryName;
import static app.util.RequestUtil.getSessionCurrentUser;

import app.util.Path.Template;
import app.util.ViewUtil;
import app.util.database.StorageInfo;
import app.util.eagle.AdvancedBankCardFlowsParameter;
import app.util.eagle.EagleResponseCode;
import app.util.eagle.EagleResponseParameter;
import app.util.eagle.HttpClient;
import app.util.tools.Verification;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class AdvancedBankCardFlowsController {

  /**
   * init advance bankcard flows
   */
  public static Route serverAdvancedBankCardFlows = (Request request, Response response) -> {
    //    LoginController.ensureUserIsLoggedIn(request, response);
    if (clientAcceptsHtml(request)) {
      return ViewUtil.render(request, new HashMap<>(), Template.ADVANCED_BANKCARD_FLOWS);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };
  /**
   * post, advance bankcard flows query
   */
  public static Route advancedBankCardFlows = (Request request, Response response) -> {
    ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();

    if (!Verification.is18ByteIdCard(getQueryIdNo(request))) {
      model.put("bankflowsIdCardQueryFailed", true);
      return ViewUtil.render(request, model, Template.ADVANCED_BANKCARD_FLOWS);
    }
    AdvancedBankCardFlowsParameter flowsParameter = AdvancedBankCardFlowsParameter.builder()
        .name(request.queryParams("name")).cardNo(request.queryParams("cardno"))
        .idNo(request.queryParams("idno"))
        .mobile(request.queryParams("mobile")).starttime(request.queryParams("starttime"))
        .endtime(request.queryParams("endtime")).build();
    String queryResult = HttpClient.fetchAdvancedBankCardFlows(flowsParameter);
    JSONObject resultJson = JSONObject.parseObject(queryResult);

    if (EagleResponseCode.SUCCESS.getResponseCode()
        .equals(resultJson.getString(EagleResponseParameter.RESCODE.getResponseParameter()))) {
      BankflowsInfoDao bankflowsInfoDao = new BankflowsInfoDao();
      model.put("bankflowsList",
          bankflowsInfoDao.toBankflowInfo(
              resultJson.getJSONArray(EagleResponseParameter.DATA.getResponseParameter())));

    } else {
      model.put("errorResponse",
          resultJson.getString(EagleResponseParameter.RESMSG.getResponseParameter()));
    }
    String info =
        resultJson.getString(EagleResponseParameter.DATA.getResponseParameter()).isEmpty() ? ""
            : resultJson.getString(EagleResponseParameter.DATA.getResponseParameter());
    StorageInfo.storageBankflows(getSessionCurrentUser(request), getQueryName(request),
        getQueryIdNo(request), info);
    return ViewUtil.render(request, model, Template.ADVANCED_BANKCARD_FLOWS);
  };
}
