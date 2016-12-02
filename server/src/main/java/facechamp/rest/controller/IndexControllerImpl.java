/**
 *
 */
package facechamp.rest.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.RestController;

import facechamp.rest.response.IndexResp;

/**
 * @since 2016. 7. 29.
 * @author Just Burrow just.burrow@lul.kr
 */
@RestController
class IndexControllerImpl extends AbstractAuthorizedController implements IndexController {
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>IndexController
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @since 2016. 7. 29.
   */
  @Override
  public IndexResp index() {
    IndexResp resp = new IndexResp();
    resp.setMessage(Instant.now().toString());
    return resp;
  }
}
