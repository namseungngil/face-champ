package facechamp.service;

import javax.annotation.PostConstruct;

import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.cmd.ReadOwnerAccountCmd;
import facechamp.domain.ClientType;
import facechamp.domain.Device;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.dto.AccessOwnerAccountResult;
import facechamp.dto.DeviceDto;
import facechamp.service.ctx.CreateDeviceCtx;
import facechamp.service.internal.DeviceInternalService;
import facechamp.util.Return;

@Service
class DeviceServiceImpl extends AbstractService implements DeviceService {
  private static final Logger   log = LoggerFactory.getLogger(DeviceService.class);

  @Autowired
  private DeviceInternalService deviceInternalService;

  @PostConstruct
  private void postConstruct() {
    this.initMapper(new PropertyMap<DeviceEntity, DeviceDto>() {
      @Override
      protected void configure() {
        this.map().setType(this.source.getType().getId());
      }
    });
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>DeviceService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Return<DeviceDto> create(CreateDeviceCmd cmd) {
    if (log.isTraceEnabled()) {
      log.trace(cmd.toString());
    }
    CreateDeviceCtx ctx = new CreateDeviceCtx(cmd.getType(), cmd.getIdentifier());
    Device device = this.deviceInternalService.create(ctx);
    return () -> this.mapper.map(device, DeviceDto.class);
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @Override
  public Return<DeviceDto> read(ReadDeviceCmd cmd) {
    if (log.isTraceEnabled()) {
      log.trace(cmd.toString());
    }
    Device device = this.deviceInternalService.get(cmd.getType(), cmd.getIdentifier());
    return () -> this.mapper.map(device, DeviceDto.class);
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @Override
  public Return<AccessOwnerAccountResult> getAccount(ReadOwnerAccountCmd cmd) {
    if (log.isTraceEnabled()) {
      log.trace(cmd.toString());
    }
    ClientType type = ClientTypes.valueOf(cmd.getType());
    Device device = this.deviceInternalService.get(type, cmd.getIdentifier());
    if (null == device) {// 신규 기기 등록.
      CreateDeviceCtx ctx = new CreateDeviceCtx(type, cmd.getIdentifier());
      return () -> new AccessOwnerAccountResult(this.deviceInternalService.create(ctx));
    }

    if (log.isTraceEnabled()) {
      log.trace(device.toString());
    }
    // TODO access account
    return () -> null;
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public Return<DeviceDto> read(long deviceKey) {
    if (0L >= deviceKey) {
      return () -> null;
    }

    Device device = this.deviceInternalService.read(deviceKey);
    return () -> null == device ? null : this.mapper.map(device, DeviceDto.class);
  }
}
