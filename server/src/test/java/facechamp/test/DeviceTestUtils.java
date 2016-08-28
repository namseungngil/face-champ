package facechamp.test;

import java.util.Random;
import java.util.UUID;

import facechamp.api.req.CreateDeviceReq;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.reposigory.DeviceRepository;
import facechamp.service.ctx.CreateDeviceCtx;
import facechamp.service.internal.DeviceInternalService;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public abstract class DeviceTestUtils {
  private static final Random R = new Random();

  /**
   * 새 기기 정보를 등록할 수 있는 임의의 리퀘스트를 만든다.
   * <b>nonce는 미설정.</b>
   *
   * @param repository
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public static CreateDeviceReq createDeviceReq(DeviceRepository repository) {
    ClientTypes type;
    String identifier;
    do {
      type = EnumUtils.random(ClientTypes.class);
      identifier = UUID.randomUUID().toString();
    } while (null != repository.findOneByTypeAndIdentifier(type, identifier));
    return new CreateDeviceReq(type.getId(), identifier);
  }

  /**
   * 임의의 값으로 초기화한 인스턴스.
   *
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public static DeviceEntity device() {
    ClientTypes type = EnumUtils.random(ClientTypes.class);
    String identifier = UUID.randomUUID().toString();
    long key;
    do {
      key = R.nextLong();
    } while (0L >= key);

    return new DeviceEntity(type, identifier, key);
  }

  /**
   * 저장하지 않고, 저장된 기기 정보와 중복되지 않은 임의의 기기 정보를 만든다.
   *
   * @param repository
   * @return
   * @author Just Burrow
   * @throws Exception
   * @since 2016. 8. 27.
   */
  public static DeviceEntity device(DeviceRepository repository) throws Exception {
    DeviceEntity device;
    do {
      device = device();
    } while (null != repository.findOneByKey(device.getKey())
        || null != repository.findOneByTypeAndIdentifier((ClientTypes) device.getType(), device.getIdentifier()));
    device = repository.save(device);
    Thread.sleep(1L);
    return device;
  }

  /**
   * 사용하지 않은 임의의 키를 반환한다.
   *
   * @param repository
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public static long freeKey(DeviceRepository repository) {
    long key;
    do {
      key = R.nextLong();
    } while (0L >= key || null != repository.findOneByKey(key));
    return key;
  }

  /**
   * 임의의 새로운 기기 정보를 저장한 후 반환한다.
   *
   * @param repository
   * @return
   * @author Just Burrow
   * @throws Exception
   * @since 2016. 8. 27.
   */
  public static DeviceEntity persistedDevice(DeviceRepository repository) throws Exception {
    return repository.save(device(repository));
  }

  /**
   * @param service
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public static DeviceEntity persistedDevice(DeviceInternalService service) {
    ClientTypes type;
    String identifier;
    do {
      type = EnumUtils.random(ClientTypes.class);
      identifier = UUID.randomUUID().toString();
    } while (null != service.get(type, identifier));

    return (DeviceEntity) service.create(new CreateDeviceCtx(type, identifier));
  }

  protected DeviceTestUtils() {
    throw new UnsupportedOperationException();
  }
}
