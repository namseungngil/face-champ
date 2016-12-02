/**
 *
 */
package facechamp.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizedResp extends AbstractResp {
  /**
   * 로그인 환영 메시지.
   */
  private String message;
}
