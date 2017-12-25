package app.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisInitialize {

  private static final String REDIS_DEFAULT_IP = "localhost";
  private static final int REDIS_DEFAULT_PORT = 6379;

  private enum InitializeReids {
    INSTANCE(new JedisPool(new JedisPoolConfig(), REDIS_DEFAULT_IP, REDIS_DEFAULT_PORT,
        Protocol.DEFAULT_TIMEOUT).getResource());

    private Jedis jedis;

    private InitializeReids(Jedis jedis) {
      this.jedis = jedis;
    }

    public Jedis init() {
      return jedis;
    }

  }

  public static Jedis getJedisResource() {
    return InitializeReids.INSTANCE.init();
  }
}
