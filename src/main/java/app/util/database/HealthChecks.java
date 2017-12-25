package app.util.database;

import com.codahale.metrics.health.HealthCheckRegistry;

public class HealthChecks {

  private HealthChecks() {
  }

  private static final HealthCheckRegistry healthCheckRegistry;

  static {
    healthCheckRegistry = new HealthCheckRegistry();
  }

  public static HealthCheckRegistry getHealthCheckRegistry() {
    return healthCheckRegistry;
  }
}
