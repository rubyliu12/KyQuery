package app.util.database;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class DbContext {
  private DSLContext dslContext = null;

  public DSLContext getDSLContext() {
    if (dslContext == null) {
      Configuration defaultConfiguration =
          new DefaultConfiguration().set(ConnectionPools.getTransactional()).set(SQLDialect.MYSQL);

      dslContext = DSL.using(defaultConfiguration);
    }

    return dslContext;
  }
}
