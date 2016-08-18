/**
 *
 */
package facechamp.domain;

import java.util.Set;

/**
 * 태그를 등록할 수 있는 오브젝트.
 *
 * @since 2016. 8. 8.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface Taggable {
  /**
   * 오브젝트에 등록된 태그 목록.
   *
   * @return
   * @since 2016. 8. 8.
   */
  public Set<Tag> getTags();
}
