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
public class IndexResp extends AbstractResp {
  private String message;
}
