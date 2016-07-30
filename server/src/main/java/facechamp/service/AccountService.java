/**
 *
 */
package facechamp.service;

import org.springframework.transaction.annotation.Transactional;

import facechamp.command.GetAccountCmd;
import facechamp.domain.Account;
import facechamp.dto.AccountDto;
import facechamp.util.Return;

/**
 * {@link Account}용 서비스.
 *
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Transactional
public interface AccountService {
  /**
   * @param cmd
   * @return
   * @since 2016. 7. 30.
   */
  public Return<AccountDto> get(GetAccountCmd cmd);
}
