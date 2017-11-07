package app;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import app.index.IndexController;
import app.login.LoginController;
import app.query.baiqishi.controller.IvsController;
import app.query.baiqishi.controller.SingleIvsQueryController;
import app.query.eagle.bankflows.AdvancedBankCardFlowsController;
import app.query.eagle.bankflows.BankFlowsQueryController;
import app.query.eagle.bankflows.BankflowsHistoryController;
import app.query.eagle.dtp.DtpInfoDao;
import app.query.eagle.dtp.DtpQueryController;
import app.query.eagle.dtp.DtpQueryHistoryController;
import app.query.eagle.dtp.DtpQueryHistoryDao;
import app.query.eagle.idverify.IdVerifyController;
import app.user.UserDao;
import app.util.ConfigsCache;
import app.util.Path;
import app.util.Path.Web;
import app.util.ViewUtil;
import app.util.requestlog.SparkUtils;
import app.util.tools.Filters;
import java.util.Objects;
import redis.clients.jedis.Jedis;

public class Application {

  // Declare dependencies
  public static UserDao userDao;
  public static DtpInfoDao dtpInfoDao;
  public static DtpQueryHistoryDao dtpQueryHistoryDao;
  private static Jedis jedis = ConfigsCache.INSTANCE.init();
  public static void main(String[] args) {
    //check redis is running or not
    Objects.requireNonNull(jedis);
    // Open SSL
    //secure("src/main/resources/https/keyikeystore.jks", "devicequeue012", null, null);
    // Instantiate your dependencies
    userDao = new UserDao();
    dtpInfoDao = new DtpInfoDao();
    dtpQueryHistoryDao = new DtpQueryHistoryDao();
    //Open RequestLog
    SparkUtils.createServerWithRequestLog();
    // Configure Spark Port
    port(4568);
    // Configure statcifiles dir
    staticFiles.location("/public");
    staticFiles.expireTime(600L);

//    enableDebugScreen();
    // Set up before-filters (called before each get/post)
    before("*", Filters.addTrailingSlashes);
    before("*", Filters.handleLocaleChange);
    // Access Limit
    before("*", Filters.accessLimit);
    // Set up routes
    //首页登录路由
    get(Path.Web.INDEX, IndexController.serveIndexPage);
    get(Path.Web.LOGIN, LoginController.serveLoginPage);
    post(Path.Web.LOGIN, LoginController.handleLoginPost);
    //户籍查询路由
    get(Path.Web.DTP_QUERY, DtpQueryController.serverQueryPage);
    get(Web.DTP_QUERY_HISTORY, DtpQueryHistoryController.serverQueryHistory);
    get(Web.DTP_QUERY_HISTORY_ONE, DtpQueryHistoryController.historyDetail);
    post(Path.Web.DTP_QUERY, DtpQueryController.dtpQuery);
    //银行流水路由
    get(Web.BANK_QUERY, BankFlowsQueryController.serverBankFlows);
    post(Web.BANK_QUERY, BankFlowsQueryController.bankflowsQuery);
    get(Web.BANK_QUERY_HISTORY, BankflowsHistoryController.serverQueryHistory);
    get(Web.BANK_QUERY_DETAIL, BankflowsHistoryController.historyDetail);
    //高级银行流水路由
    get(Web.ADVANCED_BANKCARD_FLOWS, AdvancedBankCardFlowsController.serverAdvancedBankCardFlows);
    post(Web.ADVANCED_BANKCARD_FLOWS, AdvancedBankCardFlowsController.advancedBankCardFlows);
    post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
    //身份证验证
    get(Web.ID_VERIFY, IdVerifyController.indexIdVerify);
    post(Web.ID_VERIFY, IdVerifyController.idVerify);


    //白骑士查询
    get(Web.BQS_IVS, IvsController.serverIvsQuery);
    post(Web.BQS_IVS, IvsController.ivsQueryExcel);

    get(Web.BQS_IVS_SINGLE, SingleIvsQueryController.serverIvsQuery);
    post(Web.BQS_IVS_SINGLE, SingleIvsQueryController.singleIvsQuery);
    //not found page
    get("*", ViewUtil.notFound);

    //Set up after-filters (called after each get/post)
    after("*", Filters.addGzipHeader);

  }

}
