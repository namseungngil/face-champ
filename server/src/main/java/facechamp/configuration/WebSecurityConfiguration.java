/**
 *
 */
package facechamp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import facechamp.api.security.StatelessAuthenticationFilter;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private StatelessAuthenticationFilter statelessAuthenticationFilter;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <A>WebSecurityConfigurerAdapter
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * TODO
   * (non-Javadoc)
   * @since 2016. 7. 28.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.csrf().disable();

    http.authorizeRequests()
        // Allow anonymous resource requests
        .antMatchers("/").permitAll()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("**/*.html").permitAll()
        .antMatchers("**/*.css").permitAll()
        .antMatchers("**/*.js").permitAll()

        // Allow anonymous for logins
        .antMatchers("/devices/**", "/accounts/create/*", "/accounts").anonymous()

        // All other request need to be authenticated
        .anyRequest().authenticated();
    // http.addFilterBefore(this.statelessAuthenticationFilter, HeaderWriterFilter.class);
    // http.addFilterBefore(this.statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAt(this.statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
