package facechamp.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.CreateDeviceReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;
import facechamp.cmd.ReadOwnerAccountCmd;
import facechamp.dto.AccountDto;
import facechamp.service.DeviceService;

@RestController
class DeviceControllerImpl extends AbstractController implements DeviceController {
  @Autowired
  private DeviceService deviceService;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>DeviceController
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public NonceResp createNonce() {
    return this.nonceResp(RequestMethod.POST, "/device");
  }

  @Override
  public ApiGuideResp create(@RequestBody @Valid final CreateDeviceReq req) throws IllegalNonceException {
    this.validateNonce(req, RequestMethod.POST, "/device");
    AccountDto account = this.deviceService.getAccount(new ReadOwnerAccountCmd(req.getType(), req.getIdentifier()))
        .value();
    String api = null == account ? "/account/create" : "/home";
    return new ApiGuideResp(RequestMethod.GET, api);
  }
}
