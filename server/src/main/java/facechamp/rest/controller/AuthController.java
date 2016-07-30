/**
 *
 */
package facechamp.rest.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import facechamp.rest.request.AuthorizeReq;
import facechamp.rest.response.AuthorizeTokenResp;
import facechamp.rest.response.AuthorizedResp;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@RequestMapping(value = "/auth", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface AuthController {
  /**
   * 인증을 위한 토큰 발급.
   *
   * @return
   * @since 2016. 7. 29.
   */
  @RequestMapping("/token")
  public AuthorizeTokenResp issueToken();

  /**
   * 인증 / 계정 생성 후 JWT 토큰을 생성.
   *
   * @param req
   * @param result
   * @param response
   *          TODO
   * @return
   * @since 2016. 7. 29.
   */
  @RequestMapping(value = "", method = POST)
  public AuthorizedResp authorize(@RequestBody @Valid final AuthorizeReq req, final BindingResult result,
      HttpServletResponse response);
}
