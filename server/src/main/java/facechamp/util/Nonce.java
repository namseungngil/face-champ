package facechamp.util;

import java.io.Serializable;

public class Nonce implements Serializable {
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private static final long serialVersionUID = -9133830098721064667L;

  /**
   * 발급 시각. UTC Milliseconds.
   *
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private long              timestamp;
  /**
   * 유효기간. Milliseconds.
   *
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private long              ttl;
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private String            token;

  public Nonce() {
  }

  public Nonce(long timestamp, long ttl, String token) {
    this.timestamp = timestamp;
    this.ttl = ttl;
    this.token = token;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public long getTtl() {
    return this.ttl;
  }

  public String getToken() {
    return this.token;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public String toString() {
    return new StringBuilder(Nonce.class.getSimpleName())
        .append(" [timestamp=").append(this.timestamp)
        .append(", ttl=").append(this.ttl)
        .append(", token=").append(this.token)
        .append("]").toString();
  }
}
