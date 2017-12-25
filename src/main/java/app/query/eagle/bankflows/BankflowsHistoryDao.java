package app.query.eagle.bankflows;

import app.query.generated.tables.KyBankflowsLogs;
import app.util.database.DbContext;
import com.alibaba.fastjson.JSONArray;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 * Created by Yp on 2017/5/22.
 */
public class BankflowsHistoryDao {

  private static DSLContext create = DbContext.getDslContext();

  public Result<Record> getHistoryByUser(String currentUser) {
    return create.select().from(KyBankflowsLogs.KY_BANKFLOWS_LOGS)
        .where(KyBankflowsLogs.KY_BANKFLOWS_LOGS.OPERATOR.eq(currentUser)).fetch();
  }

  public List<BankflowsInfo> getBankflowsByIdNo(String id, String idno) {
    Record queryinfo = create.select().from(KyBankflowsLogs.KY_BANKFLOWS_LOGS)
        .where(KyBankflowsLogs.KY_BANKFLOWS_LOGS.IDNO.eq(idno))
        .and(KyBankflowsLogs.KY_BANKFLOWS_LOGS.ID.equal(Integer.parseInt(id))).fetchOne();
    JSONArray jsonArray = JSONArray.parseArray(queryinfo.get("queryinfo").toString());
    BankflowsInfoDao bankflowsInfoDao = new BankflowsInfoDao();
    return bankflowsInfoDao.toBankflowInfo(jsonArray);
  }
}
