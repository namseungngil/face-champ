package facechamp.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import facechamp.api.exception.DeviceException;
import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.CreateAccountReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;
import facechamp.api.security.Role;
import facechamp.cmd.CreateAccountCmd;
import facechamp.dto.AccountDto;
import facechamp.dto.DeviceDto;
import facechamp.service.AccountService;
import facechamp.service.DeviceService;

@RestController
class AccountControllerImpl extends AbstractController implements AccountController {
  private static final Logger log = LoggerFactory.getLogger(AccountController.class);

  @Autowired
  private AccountService      accountService;
  @Autowired
  private DeviceService       deviceService;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AccountController
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public NonceResp createNonce(@PathVariable("deviceKey") long deviceKey) throws DeviceException {
    if (0L >= deviceKey) {
      throw new DeviceException("not registed : deviceKey=" + deviceKey);
    }
    DeviceDto device = this.deviceService.read(deviceKey).value();
    if (null == device) {
      throw new DeviceException("not exist : deviceKey=" + deviceKey);
    }

    return this.nonceResp(RequestMethod.POST, "/accounts");
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public ApiGuideResp create(@RequestBody @Valid CreateAccountReq req)
      throws IllegalNonceException, DeviceException {
    this.validateNonce(req, RequestMethod.POST, "/accounts");

    CreateAccountCmd cmd = new CreateAccountCmd(req.getDeviceKey(), req.getName(), req.getBio());
    AccountDto account = this.accountService.create(cmd).value();

    if (null != account) {
      this.authorize(Role.ROLE_USER, account);
    }

    return new ApiGuideResp(RequestMethod.GET, "/accounts/my");
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public void my() {
    if (log.isTraceEnabled()) {
      log.trace("/accounts/my : " + this.currentAccount());
    }
  }
}
