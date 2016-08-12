/**
 *
 */
package facechamp.rest.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import java.time.Instant;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import facechamp.command.GetAccountCmd;
import facechamp.domain.ClientType;
import facechamp.dto.AccountDto;
import facechamp.rest.request.AuthorizeReq;
import facechamp.rest.response.AuthorizeTokenResp;
import facechamp.rest.response.AuthorizedResp;
import facechamp.security.TokenAuthenticationService;
import facechamp.security.UserAuthentication;
import facechamp.service.AccountService;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@RestController
class AuthControllerImpl implements AuthController {
  public static final String         AUTHORIZE_TOKEN_FORMAT = "%d:%s";
  private static final Logger        log                    = LoggerFactory.getLogger(AuthController.class);

  @Value("${facechamp.authorizeToken.secret}")
  private String                     authorizeTokenSecret;
  @Value("${facechamp.authorizeToken.ttl}")
  private long                       authorizeTokenTtl;

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;
  @Autowired
  private AccountService             accountService;

  /**
   * 현재 시각을 기준으로 인증 토큰을 만든다.
   *
   * @return
   * @since 2016. 7. 29.
   */
  private AuthorizeTokenResp authorizeTokenResp() {
    return this.authorizeTokenResp(Instant.now().toEpochMilli());
  }

  /**
   * 인지로 받은 시각을 기준으로 인증 토큰을 만든다.
   *
   * @param now
   * @return
   * @since 2016. 7. 29.
   */
  private AuthorizeTokenResp authorizeTokenResp(final long now) {
    AuthorizeTokenResp token = new AuthorizeTokenResp();
    token.setTimestamp(now);
    token.setToken(this.authorizeToken(now));
    return token;
  }

  private String authorizeToken(final long utcMillis) {
    return sha1Hex(format(AUTHORIZE_TOKEN_FORMAT, utcMillis, this.authorizeTokenSecret));
  }

  /**
   * @param req
   * @return
   * @since 2016. 7. 30.
   */
  private boolean validateAuthorizeToken(AuthorizeReq req) {
    checkNotNull(req, "req");

    final long now = Instant.now().toEpochMilli();
    if (now > req.getTokenTimestamp() + this.authorizeTokenTtl) {
      if (log.isTraceEnabled()) {
        log.trace(format("token expired : now=%d, issued=%d, ttl=%d, over=%d, req=%s", now, req.getTokenTimestamp(),
            this.authorizeTokenTtl, (req.getTokenTimestamp() + this.authorizeTokenTtl - now), req));
      }
      return false;
    }
    String signData = format("%d:%s:%d:%s", req.getType(), req.getId(), req.getTokenTimestamp(),
        this.authorizeToken(req.getTokenTimestamp()));
    return req.getSign().equals(sha1Hex(signData));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AuthController
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 29.
   */
  @Override
  public AuthorizeTokenResp issueToken() {
    AuthorizeTokenResp resp = this.authorizeTokenResp();
    return resp;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 29.
   */
  @Override
  public AuthorizedResp authorize(@RequestBody @Valid final AuthorizeReq req, final BindingResult result,
      HttpServletResponse response) {
    checkNotNull(req, "req");
    checkNotNull(result, "result");
    checkNotNull(response, "response");

    if (!this.validateAuthorizeToken(req)) {
      throw new RuntimeException();
    }

    GetAccountCmd cmd = new GetAccountCmd();
    cmd.setType(ClientType.values()[req.getType()]);
    cmd.setIdentifier(req.getId());

    AccountDto account = this.accountService.get(cmd).value();

    User user = new User(account.getUsername(), "abcd", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    UserAuthentication authentication = new UserAuthentication(user);
    this.tokenAuthenticationService.addAuthentication(response, authentication);

    AuthorizedResp resp = new AuthorizedResp();
    resp.setMessage("aaaa");
    return resp;
  }
}
