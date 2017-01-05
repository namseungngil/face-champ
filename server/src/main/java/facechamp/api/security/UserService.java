/**
 *
 */
package facechamp.api.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import facechamp.domain.Account;
import facechamp.reposigory.AccountRepository;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Component
public class UserService implements UserDetailsService {
  @Autowired
  private AccountRepository                     accountRepository;

  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>UserDetailsService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Account account = this.accountRepository.findOne(Integer.parseInt(username));
      if (null == account) {
        throw new UsernameNotFoundException("not exist : name=" + username);
      } else {
        User user = new User(username, "", Arrays.asList(Role.ROLE_USER.authority()));
        this.detailsChecker.check(user);
        return user;
      }
    } catch (NumberFormatException e) {
      throw new UsernameNotFoundException("id format.", e);
    }
  }
}