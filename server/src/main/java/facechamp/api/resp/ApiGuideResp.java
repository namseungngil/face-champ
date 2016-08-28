package facechamp.api.resp;

import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 다음에 접근해야 할 API 정보를 제공한다.
 *
 * @author Just Burrow
 * @since 2016. 8. 27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiGuideResp extends AbstractResp {
  private RequestMethod method;
  private String        nextApi;

  public ApiGuideResp() {
  }

  public ApiGuideResp(RequestMethod method, String nextApi) {
    this.method = method;
    this.nextApi = nextApi;
  }
}
