package facechamp.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.time.Instant;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.DeviceException;
import facechamp.api.req.CreateAccountReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;
import facechamp.domain.Device;
import facechamp.service.internal.DeviceInternalService;
import facechamp.test.DeviceTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {
  @Autowired
  private AccountController     accountController;

  @Autowired
  private DeviceInternalService deviceInternalService;

  private long                  before;
  private Random                rand;

  @Before
  public void setUp() throws Exception {
    this.before = Instant.now().toEpochMilli();
    this.rand = new Random();
  }

  @Test
  public void testCreateNonceWithIllegalDeviceKey() throws Exception {
    // When & Then
    assertThatThrownBy(() -> this.accountController.createNonce(-1L))
        .isInstanceOf(DeviceException.class);
    assertThatThrownBy(() -> this.accountController.createNonce(0L))
        .isInstanceOf(DeviceException.class)
        .hasMessageContaining("not registed");
  }

  @Test
  public void testCreateNonce() throws Exception {
    // Given
    Device device = DeviceTestUtils.persistedDevice(this.deviceInternalService);

    // When
    NonceResp nonce = this.accountController.createNonce(device.getKey());

    // Then
    assertThat(nonce).isNotNull();
    assertThat(nonce.getCreate()).isGreaterThanOrEqualTo(this.before);
    assertThat(nonce.getTtl()).isGreaterThan(0L);
    assertThat(nonce.getToken()).matches("\\p{XDigit}{40}");
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    Device device = DeviceTestUtils.persistedDevice(this.deviceInternalService);
    NonceResp nonce = this.accountController.createNonce(device.getKey());
    String name = RandomStringUtils.randomAlphabetic(10);
    String bio = RandomStringUtils.randomAlphanumeric(1 + this.rand.nextInt(1024));
    CreateAccountReq req = new CreateAccountReq(device.getKey(), name, bio);
    req.setNonce(nonce);

    // When
    ApiGuideResp resp = this.accountController.create(req);

    // Then
    assertThat(resp).isNotNull()
        .extracting("method").containsExactly(RequestMethod.GET);
    assertThat(resp.getNextApi()).matches("/accounts/[1-9]\\d*");
  }

  @Test
  public void testCreateWithDuplicatedName() throws Exception {
    fail("not yet");
  }
}
