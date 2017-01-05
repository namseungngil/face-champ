package facechamp.api.resp;

import facechamp.util.Nonce;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NonceResp extends AbstractResp {
  /**
   * 발급 시각.
   *
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  private long   create;
  /**
   * 유효기간
   *
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  private long   ttl;
  /**
   * 타겟 API, 발급 시각과 TTL의 해시값.
   * nonce를 소비할 때, 위조되지 않았음을 검증할 때 사용한다.
   * 이 값으로 서버에서는 별도의 공유 저장소 없이 검증할 수 있다.
   * 타겟 API 정보는 해당 API가 알고 있다고 가정한다.
   *
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  private String token;

  public NonceResp() {
  }

  /**
   * @author Just Burrow
   * @since 2016. 8. 27.
   * @param create
   * @param ttl
   * @param sign
   */
  public NonceResp(long create, long ttl, String sign) {
    this.create = create;
    this.ttl = ttl;
    this.token = sign;
  }

  public NonceResp(Nonce nonce) {
    this.create = nonce.getTimestamp();
    this.ttl = nonce.getTtl();
    this.token = nonce.getToken();
  }
}
