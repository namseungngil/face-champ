/**
 *
 */
package facechamp.api.security;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Component
public class TokenHandler {
  @Value("${facechamp.jwt.secret}")
  private String      secret;
  @Value("${facechamp.jwt.ttl}")
  private long        ttl;

  @Autowired
  private UserService userService;

  public User parseUserFromToken(String token) {
    Instant now = Instant.now();
    Claims claims = this.getClaims(token);
    Instant issued = claims.getIssuedAt().toInstant();
    if (now.isAfter(issued.plusMillis(this.ttl))) {
      throw new JwtExpiredException(String.format("now=%s, issued=%s, ttl=%dms", now, issued, this.ttl));
    }

    String username = claims.getSubject();
    return this.userService.loadUserByUsername(username);
  }

  public String createTokenForUser(User user) {
    String jwt = Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(Date.from(Instant.now()))
        .signWith(SignatureAlgorithm.HS256, this.secret)
        .compact();
    return jwt;
  }

  public Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(this.secret)
        .parseClaimsJws(token)
        .getBody();
  }
}
