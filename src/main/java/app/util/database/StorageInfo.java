package app.util.database;

import app.query.generated.tables.KyBankflowsLogs;
import app.query.generated.tables.KyQueryLogs;
import app.query.generated.tables.records.KyBankflowsLogsRecord;
import app.query.generated.tables.records.KyQueryLogsRecord;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import org.jooq.DSLContext;

/**
 * Created by Yp on 2017/5/17.
 */
public class StorageInfo {
  private static DbContext dbContext = new DbContext();
  private static DSLContext create = dbContext.getDSLContext();

  public static void storageQueryInfo(String operator, String userName, String idNo, String info) {
    JSONObject infoJson = new JSONObject();
    infoJson.put("operator", operator);
    infoJson.put("name", userName);
    infoJson.put("idno", idNo);
    infoJson.put("querytime", new Date().toString());
    infoJson.put("queryinfo", info);

    KyQueryLogsRecord kyQueryLogsRecord = new KyQueryLogsRecord();
    kyQueryLogsRecord.from(infoJson);
    create.insertInto(KyQueryLogs.KY_QUERY_LOGS).set(kyQueryLogsRecord).onDuplicateKeyUpdate().set(KyQueryLogs.KY_QUERY_LOGS.QUERYINFO, info).execute();
  }

  public static void storageBankflows(String operator, String userName, String idNo, String info) {
    JSONObject infoJson = new JSONObject();
    infoJson.put("operator", operator);
    infoJson.put("name", userName);
    infoJson.put("idno", idNo);
    infoJson.put("querytime", new Date().toString());
    infoJson.put("queryinfo", info);

    KyBankflowsLogsRecord bankflowsLogsRecord = new KyBankflowsLogsRecord();
    bankflowsLogsRecord.from(infoJson);
    create.insertInto(KyBankflowsLogs.KY_BANKFLOWS_LOGS).set(bankflowsLogsRecord).onDuplicateKeyUpdate().set(
        KyBankflowsLogs.KY_BANKFLOWS_LOGS.QUERYINFO, info).execute();
  }
}
