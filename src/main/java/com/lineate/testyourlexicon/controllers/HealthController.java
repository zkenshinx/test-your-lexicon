package com.lineate.testyourlexicon.controllers;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HealthController {

  private final DataSource dataSource;

  @GetMapping("/health")
  public Health health() {
    var dataSourceHealthIndicator =
        new DataSourceHealthIndicator(dataSource);
    return dataSourceHealthIndicator.health();
  }
}
