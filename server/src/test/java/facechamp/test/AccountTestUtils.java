/**
 *
 */
package facechamp.test;

import static facechamp.util.Randoms.random;

import java.util.UUID;

import facechamp.command.GetAccountCmd;
import facechamp.domain.Account;
import facechamp.domain.ClientType;

/**
 * {@link Account} 테스트용 유틸리티 메서드.
 *
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
public abstract class AccountTestUtils {
  public static GetAccountCmd randomGatAccountCmd() {
    ClientType type = random(ClientType.class);
    String identifier = UUID.randomUUID().toString();

    return new GetAccountCmd(type, identifier);
  }

  protected AccountTestUtils() {
    throw new UnsupportedOperationException();
  }
}
