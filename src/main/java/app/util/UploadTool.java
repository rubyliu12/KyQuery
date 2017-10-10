package app.util;

import app.util.tools.ConfigUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.servlet.http.Part;

public class UploadTool {

  private static final ConfigUtil configUtil = ConfigUtil.getInstance();
  // private String fName = "";

  public String save(Part file) {
    String fName = "";
    String rep = "";
    LocalDateTime localDateTime = LocalDateTime.now();
    try {
      fName = localDateTime.toString().replace(":", "-") + file.getSubmittedFileName();// 中文名乱码有问题

      String fileTyle = fName.substring(fName.lastIndexOf("."), fName.length());
//      fName = now.getTimeInMillis() + fileTyle;
      String path = configUtil.getValue("upload")
          + localDateTime.getYear() + "/"
          + localDateTime.getMonthValue() + "/"
          + localDateTime.getDayOfMonth() + "/";
      File fp = new File(path);
      System.out.println(path);
      // 创建目录
      if (!fp.exists()) {
        fp.mkdirs();// 目录不存在的情况下，创建目录。
      }
      Path out = Paths.get(path + fName);

      final InputStream inputStream = file.getInputStream();
      Files.copy(inputStream, out);
      file.delete();
      rep = out.toString();

    } catch (IOException e1) {
      rep = localDateTime + e1.getMessage();
      e1.printStackTrace();
    } finally {
      file = null;
    }
    return rep;
  }

}
