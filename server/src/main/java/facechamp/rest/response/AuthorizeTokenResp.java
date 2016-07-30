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
public class AuthorizeTokenResp extends AbstractResp {
  /**
   * 타임스탬프를 기반으로 만든 인증용 토큰.
   */
  private String token;
  /**
   * 토큰 발행 시각.
   */
  private long   timestamp;
}
