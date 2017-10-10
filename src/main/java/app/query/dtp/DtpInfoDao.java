package app.query.dtp;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Yp on 2017/5/16.
 */
public class DtpInfoDao {
  public DtpInfo toDtpInfo(String name, String idNo, JSONObject jsonObject) {
    DtpInfo dtpInfo = new DtpInfo();

    dtpInfo.setName(name);
    dtpInfo.setIdNo(idNo);

    dtpInfo.setBirthday(jsonObject.getString("birthday"));
    dtpInfo.setBirthplace(jsonObject.getString("birthplace"));
    dtpInfo.setDepartment(jsonObject.getString("department"));
    dtpInfo.setDistrict(jsonObject.getString("district"));
    dtpInfo.setEducation(jsonObject.getString("education"));
    dtpInfo.setGender(jsonObject.getString("gender"));
    dtpInfo.setIdexists(jsonObject.getString("idexists"));
    dtpInfo.setNationality(jsonObject.getString("nationality"));
    dtpInfo.setMaritalstatus(jsonObject.getString("maritalstatus"));
    dtpInfo.setNamematched(jsonObject.getString("namematched"));
    dtpInfo.setLocation(jsonObject.getString("location"));

    return dtpInfo;
  }
}
