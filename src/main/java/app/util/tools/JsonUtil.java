package app.util.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

  public static String dataToJson(Object data) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, data);
      return sw.toString();
    } catch (IOException e) {
      throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON");
    }
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Object> toMap(Object object) {
    Gson gson = new Gson();
    Map<String, Object> map = new HashMap<>();
    map = gson.fromJson(gson.toJson(object), map.getClass());

    return map;
  }

}
