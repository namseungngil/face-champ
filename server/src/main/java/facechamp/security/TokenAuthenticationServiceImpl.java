/**
 *
 */
package facechamp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Component
class TokenAuthenticationServiceImpl implements TokenAuthenticationService {
  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

  @Autowired
  private TokenHandler        tokenHandler;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>TokenAuthenticationService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
    final User user = authentication.getDetails();
    response.addHeader(AUTH_HEADER_NAME, this.tokenHandler.createTokenForUser(user));
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    if (token != null) {
      final User user = this.tokenHandler.parseUserFromToken(token);
      if (user != null) {
        return new UserAuthentication(user);
      }
    }
    return null;
  }
}
