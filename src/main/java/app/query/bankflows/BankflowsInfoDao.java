package app.query.bankflows;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.util.List;

/**
 * Created by Yp on 2017/5/22.
 */
public class BankflowsInfoDao {
  public List<BankflowsInfo> toBankflowInfo(JSONArray jsonArray) {

    return  JSON.parseArray(jsonArray.toString(), BankflowsInfo.class);
    
  }
}
