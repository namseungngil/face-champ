/**
 *
 */
package facechamp.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
public class UserAuthentication implements Authentication {
  private static final long serialVersionUID = -9093552785799737962L;

  private final User        user;
  private boolean           authenticated    = true;

  public UserAuthentication(User user) {
    this.user = user;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Principal
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public String getName() {
    return this.user.getUsername();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>Authentication
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.user.getAuthorities();
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public Object getCredentials() {
    return this.user.getPassword();
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public User getDetails() {
    return this.user;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public Object getPrincipal() {
    return this.user.getUsername();
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
}