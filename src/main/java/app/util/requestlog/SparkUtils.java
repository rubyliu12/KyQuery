package app.util.requestlog;

import spark.embeddedserver.EmbeddedServers;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

public class SparkUtils {
  public static void createServerWithRequestLog() {
    EmbeddedJettyFactory factory = createEmbeddedJettyFactoryWithRequestLog();
    EmbeddedServers.add(EmbeddedServers.Identifiers.JETTY, factory);
  }

  private static EmbeddedJettyFactory createEmbeddedJettyFactoryWithRequestLog() {

    return new EmbeddedJettyFactoryConstructor().create();
  }
}
