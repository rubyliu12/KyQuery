package app.util;

import java.util.Objects;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public enum ConfigsCache {
  INSTANCE;

  public Jedis init() {
    JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379,
        Protocol.DEFAULT_TIMEOUT);
    Objects.requireNonNull(pool.getResource());
    return pool.getResource();
//    try (Jedis jedis = pool.getResource()) {
//      return jedis;
//    } catch (JedisConnectionException jce) {
//      throw new JedisConnectionException(jce.getMessage());
//    }
  }
}
