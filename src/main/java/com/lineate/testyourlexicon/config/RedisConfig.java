package com.lineate.testyourlexicon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Bean
  public Jedis jedis() {
    Jedis jedis = new Jedis(redisHost, redisPort);
    jedis.flushAll();
    return jedis;
  }
}
