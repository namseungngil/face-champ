package facechamp.api.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.DeviceException;
import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.CreateAccountReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;

@RequestMapping("/accounts")
public interface AccountController {
  /**
   * @param device
   * @return
   * @throws DeviceException
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @RequestMapping("/create/{deviceKey:[1-9]\\d*}")
  public NonceResp createNonce(@PathVariable("deviceKey") long device) throws DeviceException;

  /**
   * @param deviceKey
   *          TODO
   * @param req
   * @author Just Burrow
   * @return
   * @throws IllegalNonceException
   * @throws DeviceException
   * @since 2016. 8. 28.
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ApiGuideResp create(@RequestBody @Valid CreateAccountReq req) throws IllegalNonceException, DeviceException;

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @RequestMapping("/my")
  public void my();
}
