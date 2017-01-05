package facechamp.api.exception;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
public class IllegalNonceException extends Exception {
  /**
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  private static final long serialVersionUID = -7726797508564888164L;

  public IllegalNonceException() {
    super();
  }

  public IllegalNonceException(String message) {
    super(message);
  }
}
