/**
 *
 */
package facechamp.domain.entity;

import java.time.Instant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import facechamp.domain.Account;
import facechamp.domain.ClientType;
import facechamp.domain.HardwareIdentifier;
import facechamp.domain.Role;

/**
 * @since 2016. 7. 25.
 * @author Just Burrow just.burrow@lul.kr
 */
@Entity(name = "Account")
@EntityListeners({ AuditingEntityListener.class })
@Table(name = "user_account",
    uniqueConstraints = { @UniqueConstraint(name = "UQ_ACCOUNT_DEVICE", columnNames = { "client_type", "hw_id" }),
        @UniqueConstraint(name = "UQ_ACCOUNT_USERNAME", columnNames = { "username" }) })
public class AccountEntity implements Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  private int                        id;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "type", column = @Column(name = "client_type", nullable = false, updatable = false)),
      @AttributeOverride(name = "identifier", column = @Column(name = "hw_id", nullable = false, updatable = false)) })
  private EmbeddedHardwareIdentifier device;
  @Column(name = "username", unique = true, nullable = false, updatable = false)
  private String                     username;
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Role                       role;
  @Column(name = "create_utc", nullable = true, updatable = false)
  private Instant                    create;
  @Column(name = "update_utc", nullable = true)
  private Instant                    update;

  /**
   * JPA용.
   */
  public AccountEntity() {
  }

  /**
   * 로직용.
   */
  public AccountEntity(ClientType type, String hwId, String username) {
    this.device = new EmbeddedHardwareIdentifier(type, hwId);
    this.username = username;
    this.role = Role.INIT;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Account
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 25.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public HardwareIdentifier getDevice() {
    return this.device;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public String getUsername() {
    return this.username;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 8. 19.
   */
  @Override
  public Role getRole() {
    return this.role;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 8. 19.
   */
  @Override
  public void setRole(Role role) {
    this.role = role;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public Instant getCreate() {
    return this.create;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public Instant getUpdate() {
    return this.update;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Object
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 25.
   */
  @Override
  public int hashCode() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 25.
   */
  @Override
  public boolean equals(Object obj) {
    if (0 < this.id && null != this.device && null != this.username && !this.username.isEmpty()
        && obj instanceof AccountEntity) {
      return this.id == ((AccountEntity) obj).id;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public String toString() {
    return new StringBuilder(AccountEntity.class.getSimpleName())
        .append("[id=").append(this.id)
        .append(", device=").append(this.device)
        .append(", username=").append(this.username)
        .append(", create=").append(this.create)
        .append(", update=").append(this.update)
        .append(']').toString();
  }
}
