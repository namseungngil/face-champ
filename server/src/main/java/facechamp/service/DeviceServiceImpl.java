package facechamp.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.domain.Device;
import facechamp.dto.DeviceDto;
import facechamp.service.ctx.CreateDeviceCtx;
import facechamp.service.internal.DeviceInternalService;
import facechamp.util.Return;

@Service
class DeviceServiceImpl extends AbstractService implements DeviceService {
  @Autowired
  private DeviceInternalService deviceInternalService;

  @PostConstruct
  void postConstruct() {
    this.initMapper();
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
}
