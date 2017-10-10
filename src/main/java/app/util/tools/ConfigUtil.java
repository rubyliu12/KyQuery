package app.util.tools;

/**
 * Created by Yp on 2017/5/19.
 */
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
  private static ConfigUtil instance = null;

  private Properties properties = null;

  private ConfigUtil() {
    init();
  }

  public static ConfigUtil getInstance() {
    // System.out.println("执行Config中的getInstance方法!");

    if (instance == null) {
      instance = new ConfigUtil();
    }
    return instance;
  }

  /**
   * 初始化配置文件
   */
  public void init() {

    try {
      InputStream is = ConfigUtil.class.getResourceAsStream("/configs/config.properties");
      properties = new Properties();
      properties.load(is);

    } catch (Exception e) {
      throw new RuntimeException("Failed to get properties!");
    }
  }

  /**
   * 根据key值取得对应的value值
   *
   * @param key
   * @return
   */
  public String getValue(String key) {
    return properties.getProperty(key);
  }

  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }
}
