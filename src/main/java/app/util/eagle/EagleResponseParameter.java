package app.util.eagle;

import lombok.Getter;

public enum EagleResponseParameter {
  RESCODE("resCode"),RESMSG("resMsg"),TOKEN("token"),DATA("data");
  @Getter
  private String responseParameter;
  EagleResponseParameter(String responseParameter) {
    this.responseParameter = responseParameter;
  }
}
