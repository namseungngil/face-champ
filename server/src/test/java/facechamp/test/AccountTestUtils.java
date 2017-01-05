package facechamp.test;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import facechamp.cmd.CreateAccountCmd;
import facechamp.domain.entity.DeviceEntity;
import facechamp.reposigory.AccountRepository;
import facechamp.reposigory.DeviceRepository;
import facechamp.service.ctx.CreateAccountCtx;

public abstract class AccountTestUtils {
  private static final Random R = new Random();

  /**
   * 새로운 계정을 만들 수 있는 커맨드를 만든다.
   *
   * @param repository
   * @param deviceRepository
   * @return
   * @author Just Burrow
   * @throws Exception
   * @since 2016. 8. 28.
   */
  public static CreateAccountCmd createCmd(AccountRepository repository, DeviceRepository deviceRepository)
      throws Exception {
    DeviceEntity device = DeviceTestUtils.device(deviceRepository);
    String name;
    do {
      name = RandomStringUtils.randomAlphanumeric(2 + R.nextInt(20));
    } while (null != repository.findOneByName(name));
    String bio = "test bio : " + RandomStringUtils.randomAlphanumeric(30);

    return new CreateAccountCmd(device.getKey(), name, bio);
  }

  /**
   * @param repository
   * @param deviceRepository
   * @return
   * @author Just Burrow
   * @throws Exception
   * @since 2016. 8. 28.
   */
  public static CreateAccountCtx createCtx(AccountRepository repository, DeviceRepository deviceRepository)
      throws Exception {
    DeviceEntity device = DeviceTestUtils.device(deviceRepository);
    String name;
    do {
      name = RandomStringUtils.randomAlphanumeric(2 + R.nextInt(20));
    } while (null != repository.findOneByName(name));
    String bio = "test bio : " + RandomStringUtils.randomAlphanumeric(R.nextInt(1000));

    return new CreateAccountCtx(device, name, bio);
  }
}
