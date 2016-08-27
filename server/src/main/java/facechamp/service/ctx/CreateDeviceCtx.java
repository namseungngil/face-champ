package facechamp.service.ctx;

import facechamp.domain.ClientType;
import lombok.Data;

@Data
public class CreateDeviceCtx {
  private ClientType type;
  private String     identifier;

  public CreateDeviceCtx() {
  }

  public CreateDeviceCtx(ClientType type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }
}
