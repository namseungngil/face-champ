/**
 *
 */
package facechamp.domain.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import facechamp.domain.Language;
import facechamp.domain.Text;
import facechamp.domain.TextType;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Entity(name = "Text")
@Table(name = "master_text")
public class TextEntity implements Text {
  @Embeddable
  public static class TextIdentifier implements TextId {
    private static final long serialVersionUID = -2303186312200120023L;

    @Column(name = "language", nullable = false, updatable = false)
    private Language          language;
    @Column(name = "text_type", nullable = false, updatable = false)
    private TextType          type;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // <I>TextId
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * (non-Javadoc)
     * @since 2016. 7. 30.
     */
    @Override
    public Language language() {
      return this.language;
    }

    /*
     * (non-Javadoc)
     * @since 2016. 7. 30.
     */
    @Override
    public TextType type() {
      return this.type;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Object
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * (non-Javadoc)
     * @since 2016. 7. 30.
     */
    @Override
    public int hashCode() {
      return Objects.hash(this.type, this.language);
    }

    /*
     * (non-Javadoc)
     * @since 2016. 7. 30.
     */
    @Override
    public boolean equals(Object obj) {
      if (null != this.language && null != this.type && obj instanceof TextIdentifier) {
        TextIdentifier that = (TextIdentifier) obj;
        return this.language.equals(that.language) && this.type.equals(that.type);
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
      return String.format("(%s, %s)", this.language, this.type);
    }
  }

  @EmbeddedId
  private TextIdentifier id;
  @Column(name = "message")
  private String         text;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Text
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public TextIdentifier getId() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public String getText() {
    return this.text;
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
    return null == this.id ? 0 : this.id.hashCode();
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 30.
   */
  @Override
  public boolean equals(Object obj) {
    if (null != this.id && obj instanceof TextEntity) {
      return this.id.equals(((TextEntity) obj).id);
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
    return new StringBuilder(TextEntity.class.getSimpleName())
        .append("[id=").append(this.id)
        .append(", text=").append(this.text)
        .append(']').toString();
  }
}
