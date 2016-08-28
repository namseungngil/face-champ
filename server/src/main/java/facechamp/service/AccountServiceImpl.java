package facechamp.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.api.exception.DeviceException;
import facechamp.cmd.CreateAccountCmd;
import facechamp.domain.Account;
import facechamp.domain.Device;
import facechamp.dto.AccountDto;
import facechamp.service.ctx.CreateAccountCtx;
import facechamp.service.internal.AccountInternalService;
import facechamp.service.internal.DeviceInternalService;
import facechamp.util.Return;

@Service
class AccountServiceImpl extends AbstractService implements AccountService {
  @Autowired
  private DeviceInternalService  deviceInternalService;
  @Autowired
  private AccountInternalService accountInternalService;

  @PostConstruct
  private void postConstruct() {
    this.initMapper();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AccountService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Return<AccountDto> create(CreateAccountCmd cmd) throws DeviceException {
    Device device = this.deviceInternalService.read(cmd.getDeviceKey());
    if (null == device) {
      throw new DeviceException("not exist : deviceKey=" + cmd.getDeviceKey());
    }

    CreateAccountCtx ctx = new CreateAccountCtx(device, cmd.getName(), cmd.getBio());
    Account account = this.accountInternalService.create(ctx);

    return () -> this.mapper.map(account, AccountDto.class);
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public Return<AccountDto> read(int id) {
    Account account = this.accountInternalService.read(id);
    return () -> null == account
        ? null
        : this.mapper.map(account, AccountDto.class);
  }
}
