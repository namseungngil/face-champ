/**
 *
 */
package facechamp.command;

import facechamp.domain.ClientType;
import lombok.Data;

/**
 * 계정을 생성하거나 기존 계정을 읽는 명령.
 *
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Data
public class GetAccountCmd {
  private ClientType type;
  private String     identifier;
}
