package app.util;

import spark.Request;

public class RequestUtil {

  public static String getQueryLocale(Request request) {
    return request.queryParams("locale");
  }

  public static String getQueryUsername(Request request) {
    return request.queryParams("username");
  }

  public static String getQueryPassword(Request request) {
    return request.queryParams("password");
  }

  public static String getQueryName(Request request) {
    return request.queryParams("name");
  }

  public static String getQueryIdNo(Request request) {
    return request.queryParams("idno");
  }

  public static String getQueryMobile(Request request) {
    return request.queryParams("mobile");
  }

  public static String getQueryCardNo(Request request) {
    return request.queryParams("cardno");
  }

  public static String getQueryEmail(Request request) {
    return request.queryParams("email");
  }

  public static String getQueryCertdNo(Request request) {
    return request.queryParams("certNo");
  }

  public static String getQueryBankCardNo(Request request) {
    return request.queryParams("bankCardNo");
  }

  public static String getId(Request request) {
    return request.params("id");
  }

  public static String getIdNo(Request request) {
    return request.params("idno");
  }

  public static String getQueryLoginRedirect(Request request) {
    return request.queryParams("loginRedirect");
  }

  public static String getSessionLocale(Request request) {
    return request.session().attribute("locale");
  }

  public static String getSessionCurrentUser(Request request) {
    return request.session().attribute("currentUser");
  }

  public static boolean removeSessionAttrLoggedOut(Request request) {
    Object loggedOut = request.session().attribute("loggedOut");
    request.session().removeAttribute("loggedOut");
    return loggedOut != null;
  }

  public static String removeSessionAttrLoginRedirect(Request request) {
    String loginRedirect = request.session().attribute("loginRedirect");
    request.session().removeAttribute("loginRedirect");
    return loginRedirect;
  }

  public static boolean clientAcceptsHtml(Request request) {
    String accept = request.headers("Accept");
    return accept != null && accept.contains("text/html");
  }

  public static boolean clientAcceptsJson(Request request) {
    String accept = request.headers("Accept");
    return accept != null && accept.contains("application/json");
  }

}
