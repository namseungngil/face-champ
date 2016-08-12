/**
 *
 */
package facechamp.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.command.GetAccountCmd;
import facechamp.domain.HardwareIdentifier;
import facechamp.domain.entity.AccountEntity;
import facechamp.domain.entity.EmbeddedHardwareIdentifier;
import facechamp.dto.AccountDto;
import facechamp.reposigory.AccountRepository;
import facechamp.util.Return;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = LoggerFactory.getLogger(AccountService.class);

  @Autowired
  private AccountRepository   accountRepository;

  /**
   * @param account
   * @return
   * @since 2016. 7. 30.
   */
  private Return<AccountDto> convert(final AccountEntity account) {
    return () -> {
      AccountDto dto = new AccountDto();
      dto.setId(account.getId());
      dto.setUsername(account.getUsername());
      dto.setUpdate(account.getUpdate());
      dto.setCreate(account.getCreate());
      return dto;
    };
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AccountService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public Return<AccountDto> get(GetAccountCmd cmd) {
    if (log.isTraceEnabled()) {
      log.trace(cmd.toString());
    }

    HardwareIdentifier device = new EmbeddedHardwareIdentifier(cmd.getType(), cmd.getIdentifier());
    AccountEntity account = this.accountRepository.findOneByDevice(device);

    if (null == account) {
      account = new AccountEntity(cmd.getType(), cmd.getIdentifier(), RandomStringUtils.randomAlphanumeric(10));
      if (log.isTraceEnabled()) {
        log.trace(account.toString());
      }
      account = this.accountRepository.save(account);
    }

    return this.convert(account);
  }
}
