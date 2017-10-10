package app.query.baiqishi;

import net.sf.json.JSONObject;

public class Test {

  public static void main(String[] args) {
    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("certNo", "510225196208238938");
//    jsonObject.put("name", "周开万");
//    jsonObject.put("mobile", "13883113282");


    BQSIVSQueryParameters bp = BQSIVSQueryParameters.builder().certNo("510225196208238938").name("周开万").mobile("13883113282").build();
    BQSIVSQuery.ivsQuery(bp);
  }
}
