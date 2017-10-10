package app.util.database.impl;

import app.util.database.ConnectionPoolService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用Hikari CP作为连接池的实现,
 *
 * 官网: https://github.com/brettwooldridge/HikariCP
 *
 *
 */
public class HikariCpImpl implements ConnectionPoolService {
  private HikariDataSource ds;

  public HikariCpImpl() {
    //        HikariConfig config = new HikariConfig();
    //        config.setJdbcUrl(
    //                "jdbc:mysql://localhost:3306/kyrc?serverTimezone=UTC&autoReconnect=true&useSSL=false&characterEncoding=utf-8&useUnicode=true");
    //        config.setUsername("root");
    //        config.setPassword("admin123");
    //        config.addDataSourceProperty("cachePrepStmts", "true");
    //        config.addDataSourceProperty("prepStmtCacheSize", "250");
    //        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    HikariConfig config = new HikariConfig("/configs/hikari.properties");

    ds = new HikariDataSource(config);
  }

  /**
   * Get a connection from the pool, or timeout after connectionTimeout milliseconds.
   *
   * @return a java.sql.Connection instance
   * @throws SQLException thrown if a timeout occurs trying to obtain a connection
   */
  @Override
  public Connection getConnection() throws SQLException {
    return ds.getConnection();
  }


  @Override
  public void shutdown() {
    ds.close();
  }

  @Override
  public void evict(Connection connection) {
    ds.evictConnection(connection);
  }

}
