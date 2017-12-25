package app.util.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class DbContext {

  //private DSLContext dslContext = null;
  //
  //public DSLContext getDslContext() {
  //  if (dslContext == null) {
  //    Configuration defaultConfiguration =
  //        new DefaultConfiguration().set(ConnectionPools.getProcessing()).set(SQLDialect.MYSQL);
  //
  //    dslContext = DSL.using(defaultConfiguration);
  //  }
  //
  //  return dslContext;
  //}

  private enum ScopeContext {
    INSTANCE(DSL.using(
        new DefaultConfiguration().set(ConnectionPools.getProcessing()).set(SQLDialect.MYSQL)));

    private final DSLContext dslContext;

    private ScopeContext(DSLContext dslContext) {
      this.dslContext = dslContext;
    }

    public DSLContext getDslContext() {
      return dslContext;
    }
  }

  public static DSLContext getDslContext() {
    return ScopeContext.INSTANCE.getDslContext();
  }
}
