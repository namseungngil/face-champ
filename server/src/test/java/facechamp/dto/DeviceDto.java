package facechamp.dto;

import java.time.Instant;

import facechamp.domain.ClientType;
import facechamp.domain.Updatable;
import lombok.Data;

@Data
public class DeviceDto implements Updatable {
  private int        id;
  private long       key;
  private ClientType type;
  private String     identifier;
  private Instant    create;
  private Instant    update;
}
