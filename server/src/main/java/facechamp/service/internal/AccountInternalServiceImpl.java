package facechamp.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.domain.Account;
import facechamp.domain.entity.AccountEntity;
import facechamp.reposigory.AccountRepository;
import facechamp.service.ctx.CreateAccountCtx;

@Service
class AccountInternalServiceImpl implements AccountInternalService {
  @Autowired
  private AccountRepository accountRepository;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AccountInternalService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account create(CreateAccountCtx ctx) {
    AccountEntity account = new AccountEntity(ctx.getName(), ctx.getBio());
    account = this.accountRepository.save(account);

    ctx.getDevice().setOwner(account);

    return account;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public Account read(int id) {
    return this.accountRepository.findOne(id);
  }
}
