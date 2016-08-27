package facechamp.cmd;

import facechamp.domain.ClientType;
import lombok.Data;

@Data
public class CreateDeviceCmd {
  private ClientType type;
  private String     identifier;

  public CreateDeviceCmd() {
  }

  public CreateDeviceCmd(ClientType type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }
}
