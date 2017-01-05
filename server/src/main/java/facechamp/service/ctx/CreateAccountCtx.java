package facechamp.service.ctx;

import facechamp.domain.Device;
import lombok.Data;

@Data
public class CreateAccountCtx {
  private Device device;
  private String name;
  private String bio;

  public CreateAccountCtx() {
  }

  public CreateAccountCtx(Device device, String name, String bio) {
    this.device = device;
    this.name = name;
    this.bio = bio;
  }
}
