package facechamp.cmd;

import lombok.Data;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
@Data
public class CreateAccountCmd {
  private long   deviceKey;
  private String name;
  private String bio;

  public CreateAccountCmd() {
  }

  public CreateAccountCmd(long deviceKey, String name, String bio) {
    this.deviceKey = deviceKey;
    this.name = name;
    this.bio = bio;
  }
}
