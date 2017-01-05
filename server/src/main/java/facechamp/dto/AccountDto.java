package facechamp.dto;

import java.net.URL;
import java.time.Instant;

import facechamp.domain.Updatable;
import lombok.Data;

@Data
public class AccountDto implements Updatable {
  private int     id;
  private String  name;
  private String  bio;
  private URL     portrait;
  private Instant create;
  private Instant update;
}
