package app.query.baiqishi;

import app.util.tools.ConfigUtil;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;

/**
 * 白骑士IVS查询参数
 */
@Builder
public class BQSIVSQueryParameters {
  private static ConfigUtil configUtil = ConfigUtil.getInstance();


  @Default
  private String partnerId = configUtil.getValue("partnerId");
  @Default
  private String verifyKey = configUtil.getValue("verifyKey");
  @Default
  private String appId = configUtil.getValue("appId");
  @Default
  private String eventType = "blacklist";
  //证件号，必须
  @NonNull
  private String certNo;
  //姓名，必须
  @NonNull
  private String name;
  //手机号，必须
  @NonNull
  private String mobile;
  //电子邮箱
  private String email;
  //银行卡号
  private String bankCardNo;
  //地址信息。省+市+区/县+详细地址，其中 省+市+区/县可以为空，长度不超过256，不要包含特殊字符
  private String address;
  //ip地址。以"."分割的四段Ip，如 x.x.x.x，x为[0,255]之间的整数
  private String ip;
  //物理地址。支持格式如下：xx:xx:xx:xx:xx:xx，xx-xx-xx-xx-xx-xx，xxxxxxxxxxxx，x取值范围[0,9]之间的整数及A，B，C，D，E，F
  private String mac;
  //国际移动设备标志，15位长度数字
  private String imel;

}
