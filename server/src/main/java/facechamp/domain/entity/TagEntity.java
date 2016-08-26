/**
 *
 */
package facechamp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import facechamp.domain.Tag;

/**
 * @since 2016. 8. 8.
 * @author Just Burrow just.burrow@lul.kr
 */
@Entity(name = "Tag")
@Table(name = "user_tag")
public class TagEntity implements Tag {
  @Id
  @Column(name = "name", unique = true, nullable = false, updatable = false)
  private String name;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Tag
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 8. 8.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Object
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 8. 8.
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  /*
   * (non-Javadoc)
   * @since 2016. 8. 8.
   */
  @Override
  public boolean equals(Object obj) {
    if (null == obj) {
      return false;
    } else if (null != this.name && obj instanceof TagEntity) {
      return this.name.equals(((TagEntity) obj).name);
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * @since 2016. 8. 9.
   */
  @Override
  public String toString() {
    return new StringBuilder(TagEntity.class.getSimpleName())
        .append(" [name=").append(this.name)
        .append(']').toString();
  }
}
