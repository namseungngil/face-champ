package facechamp.api.controller;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.NonceReq;
import facechamp.api.resp.NonceResp;
import facechamp.util.BaiscNonceGenerator;
import facechamp.util.Nonce;
import facechamp.util.NonceGenerator;

abstract class AbstractController {
  @Value("${facechamp.api.nonce.ttl}")
  private long           nonceTtl;
  @Value("${facechamp.api.nonce.secret}")
  private String         nonceSecret;

  private NonceGenerator nonceGenerator;

  @PostConstruct
  private void postConstruct() {
    this.nonceGenerator = new BaiscNonceGenerator(this.nonceSecret, ":");
  }

  /**
   * 타겟 API에서 소비할 nonce를 발급한다.
   *
   * @param method
   * @param targetApi
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  protected NonceResp nonceResp(RequestMethod method, String targetApi) {
    Nonce nonce = this.nonceGenerator.generator(this.nonceTtl, method, targetApi);
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
}
