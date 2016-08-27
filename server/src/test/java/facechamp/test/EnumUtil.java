package facechamp.test;

import java.util.Random;

public abstract class EnumUtil {
  private static final Random R = new Random();

  public static <E extends Enum<?>> E random(Class<E> cls) {
    E[] values = cls.getEnumConstants();
    return values[R.nextInt(values.length)];
  }
}
