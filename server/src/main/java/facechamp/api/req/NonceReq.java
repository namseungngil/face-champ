package facechamp.api.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import facechamp.api.resp.NonceResp;
import lombok.Data;

@Data
public class NonceReq {
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @see {@link NonceResp#getCreate()}
   */
  @Min(1L)
  private long   nonceCreate;
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @see {@link NonceResp#getTtl()}
   */
  @Min(1L)
  private long   nonceTtl;
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @see {@link NonceResp#getSign()}
   */
  @NotNull
  @Size(min = 40, max = 40)
  private String nonceToken;

  public NonceReq() {
  }

  public NonceReq(long create, long ttl, String token) {
    this.nonceCreate = create;
    this.nonceTtl = ttl;
    this.nonceToken = token;
  }

  public void setNonce(NonceResp nonce) {
    this.nonceCreate = nonce.getCreate();
    this.nonceTtl = nonce.getTtl();
    this.nonceToken = nonce.getToken();
  }
}
