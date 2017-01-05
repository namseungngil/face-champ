package facechamp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import facechamp.api.exception.DeviceException;
import facechamp.cmd.CreateAccountCmd;
import facechamp.domain.Device;
import facechamp.dto.AccountDto;
import facechamp.reposigory.AccountRepository;
import facechamp.reposigory.DeviceRepository;
import facechamp.test.AccountTestUtils;
import facechamp.test.DeviceTestUtils;
import facechamp.util.Return;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest extends AbstractServiceTest {
  @Autowired
  private AccountService    accountService;

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private DeviceRepository  deviceRepository;

  @Before
  public void setUp() throws Exception {
    this.init();
  }

  @Test
  public void testCreateWithNotExistDevice() throws Exception {
    // Given
    final long deviceKey = DeviceTestUtils.freeKey(this.deviceRepository);
    CreateAccountCmd cmd = AccountTestUtils.createCmd(this.accountRepository, this.deviceRepository);
    cmd.setDeviceKey(deviceKey);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(cmd))
        .isInstanceOf(DeviceException.class)
        .hasMessageContaining("not exist");
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    CreateAccountCmd cmd = AccountTestUtils.createCmd(this.accountRepository, this.deviceRepository);

    // When
    Return<AccountDto> rv = this.accountService.create(cmd);
    AccountDto account = rv.value();

    // Then
    assertThat(account).isNotNull()
        .extracting("name", "bio", "portrait").containsExactly(cmd.getName(), cmd.getBio(), null);
    assertThat(account.getId()).isGreaterThan(0);

    Device device = this.deviceRepository.findOneByKey(cmd.getDeviceKey());
    assertThat(device.getOwner().getId())
        .isEqualTo(account.getId())
        .isGreaterThan(0);
    assertThat(device.getUpdate()).isGreaterThanOrEqualTo(this.before)
        .isGreaterThan(device.getCreate());
  }
}
