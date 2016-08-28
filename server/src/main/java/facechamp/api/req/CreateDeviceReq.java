package facechamp.api.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import facechamp.domain.ClientType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Just Burrow
 * @since 2016. 8. 27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateDeviceReq extends NonceReq {
  /**
   * @author Just Burrow
   * @since 2016. 8. 27.
   * @see {@link ClientType#getId()}
   */
  @Min(0)
  private int    type;
  /**
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @NotNull
  @Min(10)
  private String identifier;

  /**
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public CreateDeviceReq() {
  }

  /**
   * 테스트용.
   *
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @param type
   * @param identifier
   */
  public CreateDeviceReq(int type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }
}
