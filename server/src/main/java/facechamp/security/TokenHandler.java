/**
 *
 */
package facechamp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Component
public class TokenHandler {
  // @Value("${facechamp.jwt.token.secret}")
  private String      secret;

  @Autowired
  private UserService userService;

  public User parseUserFromToken(String token) {
    String username = Jwts.parser()
        .setSigningKey(this.secret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    return this.userService.loadUserByUsername(username);
  }

  public String createTokenForUser(User user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .signWith(SignatureAlgorithm.HS256, this.secret)
        .compact();
  }
}
