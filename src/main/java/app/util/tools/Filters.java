package app.util.tools;

import static app.util.RequestUtil.getQueryLocale;
import static spark.Spark.halt;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.redis.request.RedisSlidingWindowRequestRateLimiter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters {

  // If a user manually manipulates paths and forgets to add
  // a trailing slash, redirect the user to the correct path
  public static Filter addTrailingSlashes = (Request request, Response response) -> {
    if (!request.pathInfo().endsWith("/")) {
      response.redirect(request.pathInfo() + "/");
    }
  };

  // Locale change can be initiated from any page
  // The locale is extracted from the request and saved to the user's session
  public static Filter handleLocaleChange = (Request request, Response response) -> {
    if (getQueryLocale(request) != null) {
      request.session().attribute("locale", getQueryLocale(request));
      response.redirect(request.pathInfo());
    }
  };

  // Enable GZIP for all responses
  public static Filter addGzipHeader = (Request request, Response response) -> {
    response.header("Content-Encoding", "gzip");
  };
  //Access Limit with ip+url
  public static Filter accessLimit = (Request request, Response response) -> {

    StatefulRedisConnection connection = RedisClient.create("redis://localhost").connect();

    Set<RequestLimitRule> rules = Collections
        .singleton(RequestLimitRule.of(1, TimeUnit.MINUTES, 20)); // 20 request per minute, per key
    RequestRateLimiter requestRateLimiter = new RedisSlidingWindowRequestRateLimiter(connection,
        rules);

    StringBuilder key = new StringBuilder();
    key.append(request.ip()).append("_").append(request.uri());
    boolean overLimit = requestRateLimiter.overLimitWhenIncremented(key.toString());
    if (overLimit) {
      response.type("application/json");
      halt(401, "{\"message\":\"访问频繁，超出限制。\"}");
    }

  };

}
