package facechamp.cmd;

import facechamp.domain.ClientType;
import lombok.Data;

@Data
public class ReadDeviceCmd {
  private ClientType type;
  private String     identifier;

  public ReadDeviceCmd() {
  }

  public ReadDeviceCmd(ClientType type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }
}
