package facechamp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import facechamp.domain.ClientType;
import facechamp.domain.Device;

@Entity(name = "Device")
@Table(name = "user_device",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_DEV_KEY", columnNames = { "dev_key" }),
        @UniqueConstraint(name = "UQ_DEVICE_IDENTIFIER", columnNames = { "client_type", "identifier" })
    })
public class DeviceEntity extends AbstractUpdatableEntity implements Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pk", nullable = false, insertable = false, updatable = false)
  private int        id;
  @Column(name = "dev_key", nullable = false, unique = true, updatable = false)
  private long       key;
  @Column(name = "client_type", nullable = false, updatable = false)
  @Enumerated(EnumType.ORDINAL)
  private ClientType type;
  @Column(name = "identifier")
  private String     identifier;

  /**
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public DeviceEntity() {
  }

  public DeviceEntity(ClientType type, String identifier, long key) {
    this.type = type;
    this.identifier = identifier;
    this.key = key;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Device
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public long getKey() {
    return this.key;
  }

  @Override
  public ClientType getType() {
    return this.type;
  }

  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Object
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int hashCode() {
    return this.id;
  }

  @Override
  public boolean equals(Object obj) {
    if (0 < this.id && null != obj && obj instanceof DeviceEntity) {
      return this.id == ((DeviceEntity) obj).id;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  @Override
  public String toString() {
    return new StringBuilder(DeviceEntity.class.getSimpleName())
        .append(" [id=").append(this.id)
        .append(", key=").append(this.key)
        .append(", clientType=").append(this.type)
        .append(", identifier=").append(this.identifier)
        .append(']').toString();
  }
}
