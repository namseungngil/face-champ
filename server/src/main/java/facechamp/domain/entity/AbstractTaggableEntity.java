/**
 *
 */
package facechamp.domain.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import facechamp.domain.Tag;
import facechamp.domain.Taggable;

/**
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
@MappedSuperclass
public abstract class AbstractTaggableEntity implements Taggable {
  @ManyToMany(targetEntity = TagEntity.class)
  private Set<Tag> tags;

  /**
   *
   */
  protected AbstractTaggableEntity() {
    this.tags = new HashSet<>();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Taggable
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 8. 9.
   */
  @Override
  public Set<Tag> getTags() {
    return Collections.unmodifiableSet(this.tags);
  }
}
