/**
 *
 */
package facechamp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface TokenAuthenticationService {
  public Authentication getAuthentication(HttpServletRequest request);

  public void addAuthentication(HttpServletResponse response, UserAuthentication authentication);
}
