package facechamp.service;

import org.springframework.transaction.annotation.Transactional;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.dto.DeviceDto;
import facechamp.util.Return;

/**
 * @author Just Burrow
 * @since 2016. 8. 27.
 */
@Transactional
public interface DeviceService {
  /**
   * @param cmd
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public Return<DeviceDto> create(CreateDeviceCmd cmd);

  /**
   * @param cmd
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public Return<DeviceDto> read(ReadDeviceCmd cmd);
}
