package app.util;

import lombok.Getter;

public class Path {

  // The @Getter methods are needed in order to access
  // the variables from Velocity Templates
  public static class Web {

    @Getter
    public static final String INDEX = "/";
    @Getter
    public static final String LOGIN = "/login/";
    @Getter
    public static final String LOGOUT = "/logout/";
    @Getter
    public static final String DTP_QUERY = "/dtpquery/";
    @Getter
    public static final String DTP_QUERY_HISTORY = "/dtphistory/";
    @Getter
    public static final String DTP_QUERY_HISTORY_ONE = "/dtphistory/:id/:idno/";
    @Getter
    public static final String BANK_QUERY = "/bankflows/";
    @Getter
    public static final String ADVANCED_BANKCARD_FLOWS = "/V2/bankcard_flows/";
    @Getter
    public static final String BANK_QUERY_HISTORY = "/bfhistory/";
    @Getter
    public static final String BANK_QUERY_DETAIL = "/bfhistory/:id/:idno/";
    @Getter
    public static final String BQS_IVS = "/bqsivs/";
    @Getter
    public static final String BQS_IVS_SINGLE = "/bqsivssingle/";
    @Getter
    public static final String ID_VERIFY = "/idverify/";
  }

  public static class Template {

    public final static String INDEX = "/velocity/index/index.vm";
    public final static String LOGIN = "/velocity/login/login.vm";
    public static final String NOT_FOUND = "/velocity/notFound.vm";
    public static final String DTP_QUERY = "/velocity/query/eagle/dtp/dtpQuery.vm";
    public static final String DTP_QUERY_HISTORY = "/velocity/query/eagle/dtp/dtpQueryHistory.vm";
    public static final String DTP_QUERY_HISTORY_DETAIL = "/velocity/query/eagle/dtp/dtpHistoryDetail.vm";
    public static final String BANK_QUERY = "/velocity/query/eagle/bankflows/bankflowsQuery.vm";
    public static final String ADVANCED_BANKCARD_FLOWS = "/velocity/query/eagle/bankflows/advancedBankCardFlows.vm";
    public static final String BANK_QUERY_HISTORY = "/velocity/query/eagle/bankflows/bankflowsHistory.vm";
    public static final String BANK_QUERY_HISTORY_DETAIL = "/velocity/query/eagle/bankflows/bankflowsDetail.vm";
    public static final String BQS_IVS = "/velocity/query/baiqishi/ivsQuery.vm";
    public static final String BQS_IVS_SINGLE = "/velocity/query/baiqishi/singleIvsQuery.vm";

    public static final String ID_VERIFY_INDEX = "/velocity/query/eagle/idverify/idVerify.vm";
  }

}
