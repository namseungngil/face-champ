package facechamp.api.exception;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
public class DeviceException extends Exception {
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private static final long serialVersionUID = -7595738605183056876L;

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public DeviceException() {
    super();
  }

  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   * @param message
   */
  public DeviceException(String message) {
    super(message);
  }
}
