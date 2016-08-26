package facechamp.domain;

import java.time.Instant;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public interface Updatable {
  /**
   * @return 생성 시각
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public Instant getCreate();

  /**
   * @return 갱신 시각
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public Instant getUpdate();
}
