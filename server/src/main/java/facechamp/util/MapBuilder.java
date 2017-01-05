package facechamp.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link Map}을 만드는 플루언트인터페이스를 제공하는 빌더.
 */
public class MapBuilder<K, V> {
  /**
   * {@link HashMap}을 사용한 빌더를 만든다.
   *
   * @return 맵 빌더.
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> hashmap() {
    return new MapBuilder<>(new HashMap<>());
  }

  /**
   * 메서드 인자로 초기화한 {@link HashMap}을 사용한 빌더를 만든다.
   *
   * @param key
   * @param value
   * @return 맵 빌더.
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> hashmap(K key, V value) {
    return new MapBuilder<>(new HashMap<K, V>()).put(key, value);
  }

  /**
   * {@link LinkedHashMap}을 사용한 빌더를 만든다.
   *
   * @return 맵 빌더.
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> linkedhash() {
    return new MapBuilder<>(new LinkedHashMap<>());
  }

  /**
   * 메서드 인자로 초기화한 {@link LinkedHashMap}을 사용한 빌더를 만든다.
   *
   * @param key
   * @param value
   * @return 맵 빌더.
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> linkedhash(K key, V value) {
    return new MapBuilder<>(new LinkedHashMap<K, V>()).put(key, value);
  }

  /**
   * 맵 빌더를 단순히 플루언트인터페이스 래퍼로 사용한다.
   * {@link #build()} 메서드가 반환하는 맵 인스턴스는 인자로 사용한 인스턴스이다.
   *
   * @param map
   *          맵 빌더가 관리할 {@link Map} 인스턴스.
   * @return 맵 빌더.
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> builder(Map<K, V> map) {
    return new MapBuilder<>(map);
  }

  /**
   * 맵 빌더를 단순히 플루언트인터페이스 래퍼로 사용한다.
   * {@link #build()} 메서드가 반환하는 맵 인스턴스는 인자로 사용한 인스턴스이다.
   *
   * @param map
   *          맵 빌더가 관리할 {@link Map} 인스턴스.
   * @param key
   * @param value
   * @return
   * @author Just Burrow
   * @since 2016. 8. 10.
   */
  public static <K, V> MapBuilder<K, V> builder(Map<K, V> map, K key, V value) {
    return new MapBuilder<>(map).put(key, value);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private boolean   expired;
  private Map<K, V> map;

  private MapBuilder(Map<K, V> map) {
    this.expired = false;
    this.map = map;
  }

  private void check() throws IllegalStateException {
    if (this.expired) {
      throw new IllegalStateException("builder is expired.");
    } else if (null == this.map) {
      throw new IllegalStateException("map is null.");
    }
  }

  /**
   * 빌더에서 관리하는 {@link Map}에 엔트리를 추가한다.
   *
   * @param key
   *          키.
   * @param value
   *          값.
   * @return <code>this</code>
   */
  public MapBuilder<K, V> put(K key, V value) {
    this.check();
    this.map.put(key, value);
    return this;
  }

  /**
   * 빌더과 관리중이던 {@link Map}을 반환하고 빌더를 사용 불가능한 상태로 바꾼다.
   *
   * @return 빌더로 만든 {@link Map}.
   */
  public Map<K, V> build() {
    this.check();
    Map<K, V> map = this.map;
    this.expired = true;
    this.map = null;
    return map;
  }
}
