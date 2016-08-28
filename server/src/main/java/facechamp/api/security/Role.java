package facechamp.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
  ROLE_ANONYMOUS,
  ROLE_USER;

  public GrantedAuthority authority() {
    return new SimpleGrantedAuthority(this.name());
  }
}
