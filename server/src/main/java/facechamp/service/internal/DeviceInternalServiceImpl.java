package facechamp.service.internal;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import facechamp.domain.ClientType;
import facechamp.domain.Device;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.reposigory.DeviceRepository;
import facechamp.service.ctx.CreateDeviceCtx;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
@Service
class DeviceInternalServiceImpl implements DeviceInternalService {
  @Autowired
  private DeviceRepository deviceRepository;

  private Random           rand;

  @PostConstruct
  void postConstruct() {
    this.rand = new Random();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>DeviceInternalService
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Device create(CreateDeviceCtx ctx) {
    long key;
    do {
      key = this.rand.nextLong();
    } while (0L >= key || null != this.deviceRepository.findOneByKey(key));

    DeviceEntity device = new DeviceEntity((ClientTypes) ctx.getType(), ctx.getIdentifier(), key);
    device = this.deviceRepository.save(device);

    return device;
  }

  @Override
  public Device get(ClientType type, String identifier) {
    return this.deviceRepository.findOneByTypeAndIdentifier((ClientTypes) type, identifier);
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  @Override
  public Device read(long deviceKey) {
    if (0L >= deviceKey) {
      return null;
    }
    return this.deviceRepository.findOneByKey(deviceKey);
  }
}
