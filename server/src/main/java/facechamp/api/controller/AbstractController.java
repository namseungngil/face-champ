package facechamp.api.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.NonceReq;
import facechamp.api.resp.NonceResp;
import facechamp.api.security.Role;
import facechamp.api.security.UserAuthentication;
import facechamp.dto.AccountDto;
import facechamp.util.BaiscNonceGenerator;
import facechamp.util.Nonce;
import facechamp.util.NonceGenerator;

abstract class AbstractController {
  private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

  @Value("${facechamp.api.nonce.ttl}")
  private long                nonceTtl;
  @Value("${facechamp.api.nonce.secret}")
  private String              nonceSecret;

  private NonceGenerator      nonceGenerator;

  @PostConstruct
  private void postConstruct() {
    this.nonceGenerator = new BaiscNonceGenerator(this.nonceSecret, ":");
  }

  /**
   * 타겟 API에서 소비할 nonce를 발급한다.
   *
   * @param method
   * @param targetApi
   * @param params
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  protected NonceResp nonceResp(RequestMethod method, String targetApi, Object... params) {
    Object[] actualParams;
    if (0 == params.length) {
      actualParams = new Object[] { method, targetApi };
    } else {
      List<Object> list = new ArrayList<>();
      list.add(method);
      list.add(targetApi);
      list.addAll(Arrays.asList(params));
      actualParams = list.toArray();
    }
    Nonce nonce = this.nonceGenerator.generator(this.nonceTtl, actualParams);
    return new NonceResp(nonce);
  }

  /**
   * @param req
   * @param method
   * @param api
   * @throws IllegalNonceException
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  protected void validateNonce(NonceReq req, RequestMethod method, String api) throws IllegalNonceException {
    long now = Instant.now().toEpochMilli();
    if (now > req.getNonceCreate() + req.getNonceTtl()) {
      throw new IllegalNonceException(String.format("expired nonce : now=%d, req=%s", now, req));
    }

    boolean valid = this.nonceGenerator.validate(req.getNonceToken(), req.getNonceCreate(), req.getNonceTtl(), method,
        api);
    if (!valid) {
      throw new IllegalNonceException("invalid nonce : req=" + req);
    }
  }

  /**
   * 권한을 변경한다.
   *
   * @param role
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  protected void authorize(Role role) {
    if (Role.ROLE_ANONYMOUS == role) {
      SecurityContextHolder.getContext().setAuthentication(null);
    }

    // TODO 유효기간 초기화
  }

  /**
   * @param role
   * @param account
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  protected void authorize(Role role, AccountDto account) {
    if (Role.ROLE_USER != role) {
      return;
    }

    User user = new User("" + account.getId(), "", Arrays.asList(role.authority()));
    UserAuthentication authentication = new UserAuthentication(user);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  protected int currentAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null != authentication && authentication.isAuthenticated() && authentication instanceof UserAuthentication) {
      User user = ((UserAuthentication) authentication).getDetails();
      if (log.isTraceEnabled()) {
        log.trace("user=" + user);
      }

      try {
        return Integer.parseInt(user.getUsername());
      } catch (NumberFormatException e) {
        return -1;
      }
    } else {
      return -1;
    }
  }
}
