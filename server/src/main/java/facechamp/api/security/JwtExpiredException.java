package facechamp.api.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Just Burrow
 * @since 2016. 8. 29.
 */
public class JwtExpiredException extends AuthenticationException {
  /**
   * @author Just Burrow
   * @since 2016. 8. 29.
   */
  private static final long serialVersionUID = -5083674150426061233L;

  public JwtExpiredException(String msg) {
    super(msg);
  }

  public JwtExpiredException(String msg, Throwable t) {
    super(msg, t);
  }
}
