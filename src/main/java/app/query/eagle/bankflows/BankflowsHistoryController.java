package app.query.eagle.bankflows;

import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getId;
import static app.util.RequestUtil.getIdNo;
import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import app.util.Path.Template;
import app.util.ViewUtil;
import java.util.HashMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Yp on 2017/5/22.
 */
public class BankflowsHistoryController {
  private static BankflowsHistoryDao bankflowsHistoryDao = new BankflowsHistoryDao();
  public static Route serverQueryHistory = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);

    if (clientAcceptsHtml(request)) {
      HashMap<String, Object> model = new HashMap<>();
      model.put("bankflowsHistoryList", bankflowsHistoryDao.getHistoryByUser(getSessionCurrentUser(request)));
      return ViewUtil.render(request, model, Template.BANK_QUERY_HISTORY);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route historyDetail = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    if (clientAcceptsHtml(request)) {
      HashMap<String, Object> model = new HashMap<>();
      model.put("bankflowsList", bankflowsHistoryDao.getBankflowsByIdNo(getId(request), getIdNo(request)));
      return ViewUtil.render(request, model, Template.BANK_QUERY_HISTORY_DETAIL);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };
}
