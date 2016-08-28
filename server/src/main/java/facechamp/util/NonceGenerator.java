package facechamp.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
public interface NonceGenerator {
  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Instant timestamp();

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public String secret();

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public String glue();

  /**
   * @param method
   * @param api
   * @param ttl
   * @param params
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  default public Nonce generator(long ttl, Object... params) {
    long timestamp = timestamp().toEpochMilli();

    List<Object> data = new ArrayList<>();
    data.add(timestamp);
    data.add(ttl);
    data.addAll(Arrays.asList(params));
    data.add(secret());

    String input = StringUtils.join(data, glue());
    String token = DigestUtils.sha1Hex(input);

    return new Nonce(timestamp, ttl, token);
  }

  /**
   * @param nonce
   * @param timestamp
   * @param ttl
   * @param params
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  default public boolean validate(String nonce, long timestamp, long ttl, Object... params) {
    List<Object> data = new ArrayList<>();
    data.add(timestamp);
    data.add(ttl);
    data.addAll(Arrays.asList(params));
    data.add(secret());

    String expected = DigestUtils.sha1Hex(StringUtils.join(data, glue()));

    return expected.equals(nonce);
  }
}
