package facechamp.test;

import java.util.Random;

/**
 * @author Just Burrow
 * @since 2016. 8. 27.
 */
public abstract class EnumUtils {
  private static final Random R = new Random();

  public static <E extends Enum<?>> E random(Class<E> cls) {
    E[] values = cls.getEnumConstants();
    return values[R.nextInt(values.length)];
  }

  protected EnumUtils() {
    throw new UnsupportedOperationException();
  }
}
