package app.util.eagle;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class AdvancedBankCardFlowsParameter {
  @NonNull
  private String name;
  @NonNull
  private String idNo;
  @NonNull
  private String mobile;
  @NonNull
  private String cardNo;
  @NonNull
  private String starttime;
  @NonNull
  private String endtime;
  private String email;

  public String toJsonString() {
    return JSON.toJSONString(this);
  }

  public Map<String,String> toMap() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    return objectMapper.convertValue(this, Map.class);
  }
}
