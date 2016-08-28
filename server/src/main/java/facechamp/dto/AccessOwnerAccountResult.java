package facechamp.dto;

import facechamp.domain.Device;
import lombok.Data;

@Data
public class AccessOwnerAccountResult {
  private int  account = -1;
  private long deviceKey;

  public AccessOwnerAccountResult() {
  }

  public AccessOwnerAccountResult(Device device) {
    this.deviceKey = device.getKey();
  }
}
