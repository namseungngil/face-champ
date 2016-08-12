/**
 *
 */
package facechamp.domain;

import java.io.Serializable;

/**
 * 다국어 지원을 위한 텍스트.
 *
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface Text {
  public static interface TextId extends Serializable {
    public Language language();

    public TextType type();
  }

  public TextId getId();

  public String getText();
}
