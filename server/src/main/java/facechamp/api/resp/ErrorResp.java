package facechamp.api.resp;

import lombok.Data;

@Data
public class ErrorResp {
  private long   timestamp;
  private String message;
}
