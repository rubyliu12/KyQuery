package app.util.eagle;

import app.util.tools.ConfigUtil;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.stream.IntStream;

/**
 * Created by Yp on 2017/5/16.
 */
public class SignatureData {

  /**
   * 对输入的查询参数进行签名
   * @param signStr
   * @return
   */
  public static String signContent(String signStr) {
    ConfigUtil configUtil = ConfigUtil.getInstance();
    String privateKey = configUtil.getValue("yyx_sign");
    return signContent(privateKey, signStr);
  }

  private static String signContent(String privateKey, String signStr) {
    try {
      PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(privateKey));
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey key = keyFactory.generatePrivate(priPKCS8);
      // 用私钥对信息生成数字签名
      java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
      signet.initSign(key);
      signet.update(signStr.getBytes("UTF-8"));
      byte[] signed = signet.sign(); // 对信息的数字签名
      String singStr = bytesToHexStr(signed);
      return singStr;
    } catch (java.lang.Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Transform the specified byte into a Hex String form.
   */
  private static String bytesToHexStr(byte[] bcd) {
    char[] bcdLookup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
        'b', 'c', 'd', 'e', 'f'};
    StringBuffer buffer = new StringBuffer(bcd.length * 2);
    for (byte bt : bcd) {
      buffer.append(bcdLookup[(bt >>> 4) & 0x0f]).append(bcdLookup[bt & 0x0f]);
    }
    return buffer.toString();
  }

  /**
   * Transform the specified Hex String into a byte array.
   */
  private static byte[] hexStrToBytes(String s) {
    byte[] bytes;

    bytes = new byte[s.length() / 2];

    IntStream.range(0, bytes.length)
        .forEach(i -> bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16));
//    for (int i = 0; i < bytes.length; i++) {
//      bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
//    }
    return bytes;
  }
}
