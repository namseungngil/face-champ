package facechamp.service;

import org.springframework.transaction.annotation.Transactional;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.cmd.ReadOwnerAccountCmd;
import facechamp.dto.AccessOwnerAccountResult;
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

  /**
   * 등록된 하드웨어로 등록한 계정을 반환한다.
   *
   * @param cmd
   * @return 없으면 <code>null</code>.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public Return<AccessOwnerAccountResult> getAccount(ReadOwnerAccountCmd cmd);

  /**
   * @param deviceKey
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Return<DeviceDto> read(long deviceKey);
}
