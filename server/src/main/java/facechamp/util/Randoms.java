/**
 *
 */
package facechamp.util;

import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;

/**
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
public abstract class Randoms {
  public static <E extends Enum<?>> E random(Class<E> enumType) {
    List<E> list = asList(enumType.getEnumConstants());
    Collections.shuffle(list);
    return list.get(0);
  }

  public Randoms() {
    throw new UnsupportedOperationException();
  }
}
