package facechamp.service.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import facechamp.domain.ClientType;
import facechamp.domain.Device;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.service.ctx.CreateDeviceCtx;
import facechamp.test.DeviceTestUtils;
import facechamp.test.EnumUtils;
import facechamp.test.UpdatableTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class DeviceInternalServiceTest {
  @Autowired
  private EntityManager         entityManager;
  @Autowired
  private DeviceInternalService deviceInternalService;

  private Instant               before;

  @Before
  public void setUp() throws Exception {
    this.before = Instant.now();
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    final ClientTypes type = EnumUtils.random(ClientTypes.class);
    final String identifier = UUID.randomUUID().toString();
    final CreateDeviceCtx ctx = new CreateDeviceCtx(type, identifier);

    // When
    final Device device = this.deviceInternalService.create(ctx);

    // Then
    assertThat(device).isNotNull();
    assertThat(device.getId()).isGreaterThan(0);
    assertThat(device.getKey()).isGreaterThan(0L);
    assertThat(device).isNotNull()
        .extracting("type", "identifier").containsExactly(type, identifier);
    assertThat(device.getCreate()).isGreaterThanOrEqualTo(this.before);
    assertThat(device.getUpdate()).isEqualTo(device.getCreate());
  }

  @Test
  public void testGet() throws Exception {
    // Given
    DeviceEntity expected = DeviceTestUtils.device();
    final ClientType type = expected.getType();
    final String identifier = expected.getIdentifier();
    this.entityManager.persist(expected);

    // When
    final Device actual = this.deviceInternalService.get(type, identifier);

    // Then
    assertThat(actual).isNotNull()
        .isEqualTo(expected)
        .extracting("type", "identifier").containsExactly(type, identifier);
    assertThat(actual.getId()).isGreaterThan(0);
    assertThat(actual.getKey()).isEqualTo(expected.getKey())
        .isGreaterThan(0L);
    UpdatableTestUtil.createdAfter(actual, this.before);
  }
}
