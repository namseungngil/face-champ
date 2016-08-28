package facechamp.service;

import javax.annotation.PostConstruct;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.cmd.ReadOwnerAccountCmd;
import facechamp.domain.ClientType;
import facechamp.domain.Device;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.dto.AccountDto;
import facechamp.dto.DeviceDto;
import facechamp.service.ctx.CreateDeviceCtx;
import facechamp.service.internal.DeviceInternalService;
import facechamp.util.Return;

@Service
class DeviceServiceImpl extends AbstractService implements DeviceService {
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
    Device device = this.deviceInternalService.get(cmd.getType(), cmd.getIdentifier());
    return () -> this.mapper.map(device, DeviceDto.class);
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @Override
  public Return<AccountDto> getAccount(ReadOwnerAccountCmd cmd) {
    ClientType type = ClientTypes.valueOf(cmd.getType());
    Device device = this.deviceInternalService.get(type, cmd.getIdentifier());
    if (null == device) {
      CreateDeviceCtx ctx = new CreateDeviceCtx(type, cmd.getIdentifier());
      device = this.deviceInternalService.create(ctx);
      return () -> null;
    }
    // TODO access account
    return null;
  }
}
