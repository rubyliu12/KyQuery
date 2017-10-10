package app.util.database;

import app.util.database.impl.HikariCpImpl;
import java.sql.Connection;

/**
 * Created by Yp on 2017/5/16.
 */
public enum GlobalContext {

  INSTANCE;

  private final ConnectionPoolService connPollService;

  GlobalContext() {
    this.connPollService = new HikariCpImpl(); // 构造 mysql 连接池服务
  }

  public ConnectionPoolService getConnPoolService() {
    return this.connPollService;
  }

  public void evictConnection(Connection connection) {
    this.connPollService.evict(connection);
  }

  public void contextInitialized() {}

  public void contextDestroyed() {
    //销毁数据库连接池
    connPollService.shutdown();
  }
}
