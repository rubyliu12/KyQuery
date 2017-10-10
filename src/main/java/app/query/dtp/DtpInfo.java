package app.query.dtp;

/**
 * Created by Yp on 2017/5/16.
 */

import lombok.Data;

@Data
public class DtpInfo {
  String name;
  String idNo;
  String birthday;   //出生日期
  String education;  //文化程度
  String birthplace; //籍贯
  String gender; //性别
  String nationality; //名族
  String maritalstatus; //婚姻状况
  String district; //户籍地址
  String idexists; //身份证号是否存在
  String namematched; //身份证号存在时，被查询者的姓名是否匹配
  String location; //当前户籍所属省市县
  String department; //户籍登记工作单位

}
