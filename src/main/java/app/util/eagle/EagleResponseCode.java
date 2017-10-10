package app.util.eagle;

import lombok.Getter;

public enum EagleResponseCode {
  SUCCESS("0000"),FAILD("9999"),TOKENTIMEOUT("0003"),NOLOGIN("0001");
  @Getter
  private String responseCode;

  EagleResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }
}
