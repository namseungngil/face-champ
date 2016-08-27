package facechamp.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import facechamp.domain.Updatable;

public abstract class UpdatableTestUtil {
  /**
   * 지정한 시각 이후에 생성했는지 확인한다.
   *
   * @param updatable
   * @param instant
   *          포함.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static void createdAfter(Updatable updatable, Instant instant) {
    assertThat(updatable.getCreate()).isGreaterThanOrEqualTo(instant);
    assertThat(updatable.getUpdate()).isGreaterThanOrEqualTo(updatable.getCreate());
  }

  /**
   * 지정한 시각 이후에 생성했는지 확인한다.
   *
   * @param updatable
   * @param instant
   *          미포함.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static void strictCreatedAfter(Updatable updatable, Instant instant) {
    assertThat(updatable.getCreate()).isGreaterThan(instant);
    assertThat(updatable.getUpdate()).isGreaterThanOrEqualTo(updatable.getCreate());
  }

  /**
   * 지정한 시각 이후에 업데이트 했는지 확인한다.
   *
   * @param updatable
   * @param instant
   *          포함.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static void updatedAfter(Updatable updatable, Instant instant) {
    assertThat(updatable.getUpdate()).isGreaterThanOrEqualTo(updatable.getCreate())
        .isGreaterThanOrEqualTo(instant);
  }

  /**
   * 지정한 시각 이후에 업데이트 했는지 확인한다.
   *
   * @param updatable
   * @param instant
   *          미포함.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static void strictUpdatedAfter(Updatable updatable, Instant instant) {
    assertThat(updatable.getUpdate()).isGreaterThan(updatable.getCreate())
        .isGreaterThanOrEqualTo(instant);
  }

  protected UpdatableTestUtil() {
    throw new UnsupportedOperationException();
  }
}
