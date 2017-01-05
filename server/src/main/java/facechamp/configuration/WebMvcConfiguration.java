/**
 *
 */
package facechamp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @since 2016. 7. 28.
 * @author Just Burrow just.burrow@lul.kr
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <A>WebMvcConfigurerAdapter
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
  }
}
