/**
 *
 */
package facechamp.rest.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizeReq extends AbstractReq {
  @Min(0)
  @Max(2) // ClientType
  private int    type;
  @NotNull
  @Size(min = 5, max = 100)
  private String id;
  @NotNull
  @Size(min = 30)
  private String token;
  private long   tokenTimestamp;
  @NotNull
  @Size(min = 30)
  private String sign;
}
