package facechamp.api.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.CreateDeviceReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;

@RequestMapping("/device")
public interface DeviceController {
  /**
   * 기기 등록용 nonce 발급.
   *
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @RequestMapping("/create")
  public NonceResp createNonce();

  /**
   * 기기 등록.
   * <dl>
   * <dt>신규 기기</dt>
   * <dd>계정 등록 API.</dd>
   * <dt>등록 기기이지만, 계정이 없는 경우</dt>
   * <dd>계정 등록 API.</dd>
   * <dt>등록 기기이고, 계정이 있는 경우</dt>
   * <dd>홈 API.</dd>
   * </dl>
   *
   * @param req
   * @return
   * @author Just Burrow
   * @throws IllegalNonceException
   * @since 2016. 8. 27.
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ApiGuideResp create(@RequestBody @Valid CreateDeviceReq req) throws IllegalNonceException;
}
