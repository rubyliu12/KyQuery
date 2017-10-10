package app.util.requestlog;

import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

public class EmbeddedJettyFactoryConstructor {
  NCSARequestLog requestLog = new NCSARequestLog();

  EmbeddedJettyFactory create() {
    requestLog.setFilename("./src/main/resources/logs/yyyy_mm_dd.request.log");
    requestLog.setFilenameDateFormat("yyyy_MM_dd");
    requestLog.setRetainDays(90);
    requestLog.setAppend(true);
    requestLog.setExtended(true);
    requestLog.setLogCookies(false);
    requestLog.setLogTimeZone("GMT+8"); // or GMT+2 and so on.
    return new EmbeddedJettyFactory((maxThreads, minThreads, threadTimeoutMillis) -> {
      Server server;
      if (maxThreads > 0) {
        int max = maxThreads > 0 ? maxThreads : 200;
        int min = minThreads > 0 ? minThreads : 8;
        int idleTimeout = threadTimeoutMillis > 0 ? threadTimeoutMillis : '\uea60';
        server = new Server(new QueuedThreadPool(max, min, idleTimeout));
      } else {
        server = new Server();
      }

      server.setRequestLog(requestLog);
      return server;
    });
  }
}
