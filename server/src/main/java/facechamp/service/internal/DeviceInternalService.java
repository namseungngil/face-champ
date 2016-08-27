package facechamp.service.internal;

import org.springframework.transaction.annotation.Transactional;

import facechamp.domain.ClientType;
import facechamp.domain.Device;
import facechamp.service.ctx.CreateDeviceCtx;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
@Transactional
public interface DeviceInternalService {
  /**
   * 기기 정보를 생성한다.
   *
   * @param ctx
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public Device create(CreateDeviceCtx ctx);

  /**
   * 해당 기기 정보를 반환한다.
   *
   * @param type
   * @param identifier
   * @return 없으면 <code>null</code>.
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public Device get(ClientType type, String identifier);
}
