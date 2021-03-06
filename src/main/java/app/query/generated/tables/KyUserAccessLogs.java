/*
 * This file is generated by jOOQ.
*/
package app.query.generated.tables;


import app.query.generated.Keyirisk;
import app.query.generated.Keys;
import app.query.generated.tables.records.KyUserAccessLogsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 控制访问日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class KyUserAccessLogs extends TableImpl<KyUserAccessLogsRecord> {

    private static final long serialVersionUID = 1119319786;

    /**
     * The reference instance of <code>keyirisk.ky_user_access_logs</code>
     */
    public static final KyUserAccessLogs KY_USER_ACCESS_LOGS = new KyUserAccessLogs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<KyUserAccessLogsRecord> getRecordType() {
        return KyUserAccessLogsRecord.class;
    }

    /**
     * The column <code>keyirisk.ky_user_access_logs.id</code>.
     */
    public final TableField<KyUserAccessLogsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>keyirisk.ky_user_access_logs.username</code>.
     */
    public final TableField<KyUserAccessLogsRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>keyirisk.ky_user_access_logs.access_date</code>.
     */
    public final TableField<KyUserAccessLogsRecord, String> ACCESS_DATE = createField("access_date", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>keyirisk.ky_user_access_logs.ipaddress</code>.
     */
    public final TableField<KyUserAccessLogsRecord, String> IPADDRESS = createField("ipaddress", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

    /**
     * Create a <code>keyirisk.ky_user_access_logs</code> table reference
     */
    public KyUserAccessLogs() {
        this("ky_user_access_logs", null);
    }

    /**
     * Create an aliased <code>keyirisk.ky_user_access_logs</code> table reference
     */
    public KyUserAccessLogs(String alias) {
        this(alias, KY_USER_ACCESS_LOGS);
    }

    private KyUserAccessLogs(String alias, Table<KyUserAccessLogsRecord> aliased) {
        this(alias, aliased, null);
    }

    private KyUserAccessLogs(String alias, Table<KyUserAccessLogsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "控制访问日志");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Keyirisk.KEYIRISK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<KyUserAccessLogsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_KY_USER_ACCESS_LOGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<KyUserAccessLogsRecord> getPrimaryKey() {
        return Keys.KEY_KY_USER_ACCESS_LOGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<KyUserAccessLogsRecord>> getKeys() {
        return Arrays.<UniqueKey<KyUserAccessLogsRecord>>asList(Keys.KEY_KY_USER_ACCESS_LOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyUserAccessLogs as(String alias) {
        return new KyUserAccessLogs(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public KyUserAccessLogs rename(String name) {
        return new KyUserAccessLogs(name, null);
    }
}
