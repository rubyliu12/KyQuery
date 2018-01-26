package app.query.eagle.idverify;

import com.google.common.base.Strings;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64ImageUtil {

  // 图片转化成base64字符串
  public static String getImageStr(String imgFile) {
    // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    //		String imgFile = "C:\\Users\\Administrator\\Desktop\\1_hfhwfw.jpg";// 待处理的图片
    InputStream in = null;
    byte[] data = null;
    // 读取图片字节数组
    try {
      in = new FileInputStream(imgFile);
      data = new byte[in.available()];
      in.read(data);
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 对字节数组Base64编码
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
  }

  // base64字符串转化成图片
  public static String generateImage(String imgStr, String imagePath,
      HttpServletRequest req) { // 对字节数组字符串进行Base64解码并生成图片
    if (imgStr == null) // 图像数据为空
    {
      return null;
    }
    String rootpath = req.getSession().getServletContext().getRealPath("/");
    String file = "a" + File.separator + "images" + File.separator + imagePath;

    String filename = rootpath + file;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      // Base64解码
      byte[] b = decoder.decodeBuffer(imgStr);
      for (int i = 0; i < b.length; ++i) {
        if (b[i] < 0) {// 调整异常数据
          b[i] += 256;
        }
      }
      OutputStream out = new FileOutputStream(filename);
      out.write(b);
      out.flush();
      out.close();
      return "images" + File.separator + imagePath;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * base64StringtoImage:用于处理优分身份核查肖像图片.
   *
   * @author yangliu
   * @date 2016年8月19日
   * @since JDK 1.8
   */
  public static String base64StringtoImage(String imgStr, String imagePath,
      HttpServletRequest req) {
    if (imgStr == null) // 图像数据为空
    {
      return null;
    }
    String rootpath = req.getSession().getServletContext().getRealPath("/");
    String file = "a" + File.separator + "images" + File.separator + imagePath;

    String filename = rootpath + file;
    try {
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] bytes = decoder.decodeBuffer(imgStr);
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      BufferedImage bi = ImageIO.read(bais);
      File imgFile = new File(filename);//可以是jpg,png,gif格式
      ImageIO.write(bi, "jpg", imgFile);
      bais.close();
      return "images" + File.separator + imagePath;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * result photo str to base64 and can direct display in html
   */
  public static String strtoBase64(String imgStr) {
    if (Strings.isNullOrEmpty(imgStr)) {// 图像数据为空
      return "";
    }

    try {
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] bytes = decoder.decodeBuffer(imgStr);
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      BufferedImage bi = ImageIO.read(bais);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(bi, "jpg", bos);
      byte[] imageBytes = bos.toByteArray();

      BASE64Encoder encoder = new BASE64Encoder();
      bais.close();
      bos.close();
      return encoder.encode(imageBytes);

    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }

  }
}
