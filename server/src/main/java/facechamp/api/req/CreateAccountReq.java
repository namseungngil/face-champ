package facechamp.api.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateAccountReq extends NonceReq {
  @Min(1L)
  private long   deviceKey;
  @NotNull
  @Size(min = 3, max = 30)
  private String name;
  @Size(max = 1024)
  private String bio;

  public CreateAccountReq() {
  }

  public CreateAccountReq(long deviceKey, String name, String bio) {
    this.deviceKey = deviceKey;
    this.name = name;
    this.bio = bio;
  }
}
