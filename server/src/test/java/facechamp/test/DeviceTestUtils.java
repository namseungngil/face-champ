package facechamp.test;

import java.util.Random;
import java.util.UUID;

import facechamp.domain.ClientType;
import facechamp.domain.entity.DeviceEntity;
import facechamp.reposigory.DeviceRepository;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public abstract class DeviceTestUtils {
  private static final Random R = new Random();

  /**
   * 임의의 값으로 초기화한 인스턴스.
   *
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public static DeviceEntity device() {
    ClientType type = ClientType.values()[DeviceTestUtils.R.nextInt(ClientType.values().length)];
    String identifier = UUID.randomUUID().toString();
    long key;
    do {
      key = R.nextLong();
    } while (0L >= key);

    return new DeviceEntity(type, identifier, key);
  }

  /**
   * @param repository
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static DeviceEntity device(DeviceRepository repository) {
    DeviceEntity device;
    do {
      device = device();
    } while (null != repository.findOneByKey(device.getKey())
        || null != repository.findOneByTypeAndIdentifier(device.getType(), device.getIdentifier()));
    return repository.save(device);
  }
}
