/**
 *
 */
package facechamp.domain.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import facechamp.domain.Account;
import facechamp.domain.Player;

/**
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
@Entity(name = "Player")
@Table(name = "user_player")
@AssociationOverride(name = "tags",
    joinTable = @JoinTable(name = "rel_player_tag",
        joinColumns = { @JoinColumn(name = "player", foreignKey = @ForeignKey(name = "REL_TAG_PLAYER")) },
        inverseJoinColumns = { @JoinColumn(name = "tag", foreignKey = @ForeignKey(name = "REL_PLAYER_TAG")) }))
public class PlayerEntity extends AbstractTaggableEntity implements Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int     id;

  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = "owner",
      foreignKey = @ForeignKey(name = "FK_PLAYER_OWNER"),
      referencedColumnName = "id",
      nullable = false,
      updatable = false)
  private Account owner;

  /**
   *
   */
  public PlayerEntity() {
  }

  /**
   * @param owner
   */
  public PlayerEntity(Account owner) {
    this.owner = owner;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Player
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 8. 9.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 8. 9.
   */
  @Override
  public Account getOwner() {
    return this.owner;
  }
}
