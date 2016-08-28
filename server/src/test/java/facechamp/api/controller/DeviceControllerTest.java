package facechamp.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import facechamp.api.exception.IllegalNonceException;
import facechamp.api.req.CreateDeviceReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;
import facechamp.reposigory.DeviceRepository;
import facechamp.test.DeviceTestUtils;
import facechamp.util.BaiscNonceGenerator;
import facechamp.util.Nonce;
import facechamp.util.NonceGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceControllerTest {
  @Value("${facechamp.api.nonce.ttl}")
  private long             nonceTtl;
  @Value("${facechamp.api.nonce.secret}")
  private String           nonceSecret;

  @Autowired
  private DeviceController deviceController;

  @Autowired
  private DeviceRepository deviceRepository;

  private long             before;

  @Before
  public void setUp() throws Exception {
    this.before = Instant.now().toEpochMilli();
  }

  @Test
  public void testCreateNonce() throws Exception {
    // When
    NonceResp nonce = this.deviceController.createNonce();

    // Then
    assertThat(nonce).isNotNull()
        .extracting("ttl").containsExactly(this.nonceTtl);
    assertThat(nonce.getCreate()).isGreaterThanOrEqualTo(this.before);
    assertThat(nonce.getToken()).matches("\\p{Alnum}{40}");
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    NonceResp nonce = this.deviceController.createNonce();
    CreateDeviceReq req = DeviceTestUtils.createDeviceReq(this.deviceRepository);
    req.setNonce(nonce);

    // When
    ApiGuideResp resp = this.deviceController.create(req);

    // Then
    assertThat(resp).isNotNull()
        .extracting("method").containsExactly(RequestMethod.GET);
    assertThat(resp.getNextApi()).isEqualTo("/accounts/create/{deviceKey}");
    assertThat((long) resp.getProperty("deviceKey")).isNotNull()
        .isGreaterThan(0L);
  }

  @Test
  public void testCreateWithIllegalNonce() throws Exception {
    // Given
    NonceGenerator generator = new BaiscNonceGenerator(this.nonceSecret, ":");
    Nonce nonce = generator.generator(this.nonceTtl);
    CreateDeviceReq req = DeviceTestUtils.createDeviceReq(this.deviceRepository);
    req.setNonce(new NonceResp(nonce));

    // When & Then
    assertThatThrownBy(() -> this.deviceController.create(req))
        .isInstanceOf(IllegalNonceException.class)
        .hasMessageContaining("invalid");
  }

  @Test
  public void testCreateWithExpiredNonce() throws Exception {
    // Given
    NonceGenerator generator = new BaiscNonceGenerator(this.nonceSecret, ":");
    Nonce nonce = generator.generator(1L, RequestMethod.POST, "/device");
    CreateDeviceReq req = DeviceTestUtils.createDeviceReq(this.deviceRepository);
    req.setNonce(new NonceResp(nonce));

    // When & Then
    Thread.sleep(10L);
    assertThatThrownBy(() -> this.deviceController.create(req))
        .isInstanceOf(IllegalNonceException.class)
        .hasMessageContaining("expired nonce");
  }
}
