package app.login;

import static app.util.RequestUtil.clientAcceptsHtml;
import static app.util.RequestUtil.getQueryLoginRedirect;
import static app.util.RequestUtil.getQueryPassword;
import static app.util.RequestUtil.getQueryUsername;
import static app.util.RequestUtil.removeSessionAttrLoggedOut;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;

import app.user.UserController;
import app.util.Path;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

  /**
   * init login page with some params
   */
  public static Route serveLoginPage = (Request request, Response response) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("loggedOut", removeSessionAttrLoggedOut(request));
    model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
    return ViewUtil.render(request, model, Path.Template.LOGIN);
  };

  /**
   * login controller with auth check
   */
  public static Route handleLoginPost = (Request request, Response response) -> {
    Map<String, Object> model = new HashMap<>();
    if (!UserController.authenticate(getQueryUsername(request), getQueryPassword(request))) {
      model.put("authenticationFailed", true);
      return ViewUtil.render(request, model, Path.Template.LOGIN);
    }
    model.put("authenticationSucceeded", true);
    request.session().attribute("currentUser", getQueryUsername(request));
    if (getQueryLoginRedirect(request) != null) {
      response.redirect(getQueryLoginRedirect(request));
    }
    if (clientAcceptsHtml(request)) {
      response.redirect("/");
    }
    return ViewUtil.render(request, model, Path.Template.LOGIN);
  };
  /**
   * logout controller
   */
  public static Route handleLogoutPost = (Request request, Response response) -> {
    request.session().removeAttribute("currentUser");
    request.session().attribute("loggedOut", true);
    response.redirect(Path.Web.LOGIN);
    return null;
  };

  // The origin of the request (request.pathInfo()) is saved in the session so
  // the user can be redirected back after login
  public static void ensureUserIsLoggedIn(Request request, Response response) {
    if (request.session().attribute("currentUser") == null) {
      request.session().attribute("loginRedirect", request.pathInfo());
      response.redirect(Path.Web.LOGIN);
    }
  }


}
