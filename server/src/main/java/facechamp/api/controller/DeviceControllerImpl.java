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
import facechamp.api.security.Role;
import facechamp.cmd.ReadOwnerAccountCmd;
import facechamp.dto.AccessOwnerAccountResult;
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
    this.authorize(Role.ROLE_ANONYMOUS);
    return this.nonceResp(RequestMethod.POST, "/devices");
  }

  @Override
  public ApiGuideResp create(@RequestBody @Valid final CreateDeviceReq req) throws IllegalNonceException {
    this.validateNonce(req, RequestMethod.POST, "/devices");

    AccessOwnerAccountResult result = this.deviceService
        .getAccount(new ReadOwnerAccountCmd(req.getType(), req.getIdentifier()))
        .value();

    String api;
    if (0 >= result.getAccount()) {
      this.authorize(Role.ROLE_ANONYMOUS);
      api = "/accounts/create/{deviceKey}";
    } else {
      api = "/home";
    }
    ApiGuideResp resp = new ApiGuideResp(RequestMethod.GET, api);
    resp.addProperty("deviceKey", result.getDeviceKey());
    return resp;
  }
}
