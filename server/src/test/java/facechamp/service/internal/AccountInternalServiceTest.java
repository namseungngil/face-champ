package facechamp.service.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import facechamp.domain.entity.AccountEntity;
import facechamp.reposigory.AccountRepository;
import facechamp.reposigory.DeviceRepository;
import facechamp.service.ctx.CreateAccountCtx;
import facechamp.test.AccountTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountInternalServiceTest extends AbstractInternalServiceTest {
  @Autowired
  private AccountInternalService accountInternalService;

  @Autowired
  private AccountRepository      accountRepository;
  @Autowired
  private DeviceRepository       deviceRepository;

  @Before
  public void setUp() throws Exception {
    this.init();
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    CreateAccountCtx ctx = AccountTestUtils.createCtx(this.accountRepository, this.deviceRepository);

    // When
    AccountEntity account = (AccountEntity) this.accountInternalService.create(ctx);

    // Then
    assertThat(account).isNotNull();
    assertThat(account.getCreate()).isGreaterThanOrEqualTo(this.before);
    assertThat(account.getUpdate()).isEqualTo(account.getCreate());
    assertThat(ctx.getDevice().getOwner()).isEqualTo(account);
  }

  @Test
  public void testCreateWithNotExistDevice() throws Exception {
    fail("not yet.");
  }

  @Test
  public void testCreateWithExistOwnerAccount() throws Exception {
    fail("not yet.");
  }
}
