/**
 *
 */
package facechamp.domain;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface HardwareIdentifier {
  public ClientType getType();

  public String getIdentifier();
}
