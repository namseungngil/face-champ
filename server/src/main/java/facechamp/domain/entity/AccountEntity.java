package facechamp.domain.entity;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import facechamp.domain.Account;

@Entity(name = "Account")
@Table(name = "user_account",
    uniqueConstraints = { @UniqueConstraint(name = "UQ_ACCOUNT_USER_NAME", columnNames = { "user_name" }) })
public class AccountEntity extends AbstractUpdatableEntity implements Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pk", nullable = false, insertable = false, updatable = false)
  private int    id;
  @Column(name = "user_name", unique = true, nullable = false)
  private String name;
  @Column(name = "user_bio")
  private String bio;
  @Column(name = "portrait")
  private URL    portrait;

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public AccountEntity() {
  }

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @param name
   */
  public AccountEntity(String name) {
    this.setName(name);
  }

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @param name
   * @param bio
   */
  public AccountEntity(String name, String bio) {
    this.setName(name);
    this.setBio(bio);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Account
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    if (null == name) {
      throw new NullPointerException("name");
    } else if (!name.matches("\\S.{0,43}\\S")) {
      throw new IllegalArgumentException("illegal name : " + name);
    }
    this.name = name;
  }

  @Override
  public String getBio() {
    return this.bio;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public void setBio(String bio) {
    this.bio = bio;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public URL getPortrait() {
    return this.portrait;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public void setPortrait(URL portraitUrl) {
    this.portrait = portraitUrl;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Object
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public int hashCode() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public boolean equals(Object obj) {
    if (0 < this.id && null != obj && obj instanceof AccountEntity) {
      return this.id == ((AccountEntity) obj).id;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public String toString() {
    return new StringBuilder(AccountEntity.class.getSimpleName())
        .append(" [id=").append(this.id)
        .append(", name=").append(this.name)
        .append(", bio=").append(this.bio)
        .append(", portrait=").append(this.portrait)
        .append("]").toString();
  }
}
