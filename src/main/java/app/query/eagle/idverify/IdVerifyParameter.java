package app.query.eagle.idverify;

import lombok.Builder;
import lombok.NonNull;
@Builder
public class IdVerifyParameter {
  @NonNull
  private String name;
  @NonNull
  private String idNo;
}
