package facechamp.api.resp;

import java.util.HashMap;
import java.util.Map;

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
  private RequestMethod       method;
  private String              nextApi;
  private Map<String, Object> properties;

  public ApiGuideResp() {
  }

  public ApiGuideResp(RequestMethod method, String nextApi) {
    this.method = method;
    this.nextApi = nextApi;
  }

  /**
   * API 관련된 추가 정보를 추가한다.
   *
   * @param key
   * @param value
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public void addProperty(String key, Object value) {
    if (null == this.properties) {
      this.properties = new HashMap<>();
    }
    if (this.properties.containsKey(key)) {
      throw new IllegalStateException(
          String.format("already exists : key=%s, value=%s", key, this.properties.get(key)));
    } else {
      this.properties.put(key, value);
    }
  }

  /**
   * @param key
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Object getProperty(String key) {
    return null == this.properties ? null : this.properties.get(key);
  }
}
