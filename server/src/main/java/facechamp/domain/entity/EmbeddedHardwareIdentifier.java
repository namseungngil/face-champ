/**
 *
 */
package facechamp.domain.entity;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import facechamp.domain.ClientType;
import facechamp.domain.HardwareIdentifier;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Embeddable
public class EmbeddedHardwareIdentifier implements HardwareIdentifier {
  @Enumerated(EnumType.ORDINAL)
  private ClientType type;
  private String     identifier;

  public EmbeddedHardwareIdentifier() {
  }

  public EmbeddedHardwareIdentifier(ClientType type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>HardwareIdentifier
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public ClientType getType() {
    return this.type;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Object
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.type, this.identifier);
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public boolean equals(Object obj) {
    if (null == obj) {
      return false;
    } else if (null != this.type && null != this.identifier && obj instanceof EmbeddedHardwareIdentifier) {
      EmbeddedHardwareIdentifier that = (EmbeddedHardwareIdentifier) obj;
      return this.type.equals(that.type) && this.identifier.equals(that.identifier);
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
    return String.format("%s-%s", this.type, this.identifier);
  }
}
