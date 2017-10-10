/*
 * This file is generated by jOOQ.
*/
package app.query.generated.tables.records;


import app.query.generated.tables.KyBankflowsLogs;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class KyBankflowsLogsRecord extends UpdatableRecordImpl<KyBankflowsLogsRecord> implements Record7<Integer, String, String, String, String, String, String> {

    private static final long serialVersionUID = 1371904701;

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.operator</code>.
     */
    public void setOperator(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.operator</code>.
     */
    public String getOperator() {
        return (String) get(1);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.idno</code>.
     */
    public void setIdno(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.idno</code>.
     */
    public String getIdno() {
        return (String) get(3);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.querytime</code>.
     */
    public void setQuerytime(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.querytime</code>.
     */
    public String getQuerytime() {
        return (String) get(4);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.transactionId</code>.
     */
    public void setTransactionid(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.transactionId</code>.
     */
    public String getTransactionid() {
        return (String) get(5);
    }

    /**
     * Setter for <code>keyirisk.ky_bankflows_logs.queryinfo</code>.
     */
    public void setQueryinfo(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>keyirisk.ky_bankflows_logs.queryinfo</code>.
     */
    public String getQueryinfo() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.IDNO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.QUERYTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.TRANSACTIONID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return KyBankflowsLogs.KY_BANKFLOWS_LOGS.QUERYINFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIdno();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getQuerytime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getTransactionid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getQueryinfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value2(String value) {
        setOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value4(String value) {
        setIdno(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value5(String value) {
        setQuerytime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value6(String value) {
        setTransactionid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord value7(String value) {
        setQueryinfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KyBankflowsLogsRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached KyBankflowsLogsRecord
     */
    public KyBankflowsLogsRecord() {
        super(KyBankflowsLogs.KY_BANKFLOWS_LOGS);
    }

    /**
     * Create a detached, initialised KyBankflowsLogsRecord
     */
    public KyBankflowsLogsRecord(Integer id, String operator, String name, String idno, String querytime, String transactionid, String queryinfo) {
        super(KyBankflowsLogs.KY_BANKFLOWS_LOGS);

        set(0, id);
        set(1, operator);
        set(2, name);
        set(3, idno);
        set(4, querytime);
        set(5, transactionid);
        set(6, queryinfo);
    }
}