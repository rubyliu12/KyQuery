package app.query.eagle.dtp;

import static app.Application.dtpQueryHistoryDao;
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
 * Created by Yp on 2017/5/17.
 */
public class DtpQueryHistoryController {

  public static Route serverQueryHistory = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    if (clientAcceptsHtml(request)) {
      HashMap<String, Object> model = new HashMap<>();
      model.put("historyList", dtpQueryHistoryDao.getHistoryByUser(getSessionCurrentUser(request)));
      return ViewUtil.render(request, model, Template.DTP_QUERY_HISTORY);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route historyDetail = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    if (clientAcceptsHtml(request)) {
      HashMap<String, Object> model = new HashMap<>();
      model.put("historyDetail",
          dtpQueryHistoryDao.getDtpInfoByIdNo(getId(request), getIdNo(request)));
      return ViewUtil.render(request, model, Template.DTP_QUERY_HISTORY_DETAIL);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };
}
