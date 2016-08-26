package facechamp.domain.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import facechamp.domain.Updatable;

@MappedSuperclass
public abstract class AbstractUpdatableEntity implements Updatable {
  /**
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  @Column(name = "create_utc", nullable = false, updatable = false)
  private Instant create;
  @Column(name = "update_utc", nullable = false)
  private Instant update;

  @PrePersist
  public void prePersist() {
    this.update = this.create = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.update = Instant.now();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Updatable
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Instant getCreate() {
    return this.create;
  }

  @Override
  public Instant getUpdate() {
    return this.update;
  }
}
