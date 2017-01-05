package facechamp.service;

import org.springframework.transaction.annotation.Transactional;

import facechamp.api.exception.DeviceException;
import facechamp.cmd.CreateAccountCmd;
import facechamp.dto.AccountDto;
import facechamp.util.Return;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
@Transactional
public interface AccountService {
  /**
   * @param cmd
   * @return
   * @author Just Burrow
   * @throws DeviceException
   * @since 2016. 8. 28.
   */
  public Return<AccountDto> create(CreateAccountCmd cmd) throws DeviceException;

  /**
   * @param id
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Return<AccountDto> read(int id);
}
