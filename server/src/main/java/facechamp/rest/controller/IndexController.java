/**
 *
 */
package facechamp.rest.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.RequestMapping;

import facechamp.rest.response.IndexResp;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@RequestMapping(value = "/index", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface IndexController {
  @RequestMapping("")
  public IndexResp index();
}
