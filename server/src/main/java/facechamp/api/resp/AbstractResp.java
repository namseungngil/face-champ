package facechamp.api.resp;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class AbstractResp {
  private Map<String, Object> meta;

  public AbstractResp() {
    this.meta = new HashMap<>();
  }

  @SuppressWarnings({ "unchecked" })
  public void addMeta(String key, Object value) {
    if (!key.matches("[a-z]\\w*")) {
      throw new IllegalArgumentException("key format.");
    }

    synchronized (this.meta) {
      if (!this.meta.containsKey(key) || null == this.meta.get(key)) {
        this.meta.put(key, value);
        return;
      }

      Object preVal = this.meta.get(key);
      try {
        Collection<Object> col = (Collection<Object>) preVal;
        col.add(value);
      } catch (Exception e) {
        this.meta.put(key, Arrays.asList(preVal, value));
      }
    }
  }
}
