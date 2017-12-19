package app.query.eagle.dtp;

import app.query.generated.tables.KyQueryLogs;
import app.util.database.DbContext;
import com.alibaba.fastjson.JSONObject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 * Created by Yp on 2017/5/17.
 */
public class DtpQueryHistoryDao {

  private static DSLContext create = DbContext.getDSLContext();

  public Result<Record> getHistoryByUser(String currentUser) {
    return create.select().from(KyQueryLogs.KY_QUERY_LOGS)
        .where(KyQueryLogs.KY_QUERY_LOGS.OPERATOR.eq(currentUser)).fetch();
  }

  public DtpInfo getDtpInfoByIdNo(String id, String idno) {
    Record queryinfo = create.select().from(KyQueryLogs.KY_QUERY_LOGS)
        .where(KyQueryLogs.KY_QUERY_LOGS.IDNO.eq(idno))
        .and(KyQueryLogs.KY_QUERY_LOGS.ID.equal(Integer.parseInt(id))).fetchOne();
    JSONObject jsonObject = JSONObject.parseObject(queryinfo.get("queryinfo").toString());
    DtpInfoDao dtpInfoDao = new DtpInfoDao();
    return dtpInfoDao.toDtpInfo(queryinfo.get("name").toString(), queryinfo.get("idno").toString(), jsonObject);
  }
}
