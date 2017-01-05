package facechamp.cmd;

import lombok.Data;

@Data
public class ReadOwnerAccountCmd {
  private int    type;
  private String identifier;

  public ReadOwnerAccountCmd() {
  }

  public ReadOwnerAccountCmd(int type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }
}
