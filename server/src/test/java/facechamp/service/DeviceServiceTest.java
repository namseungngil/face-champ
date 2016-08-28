package facechamp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import facechamp.cmd.CreateDeviceCmd;
import facechamp.cmd.ReadDeviceCmd;
import facechamp.domain.ClientType;
import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;
import facechamp.dto.DeviceDto;
import facechamp.reposigory.DeviceRepository;
import facechamp.test.DeviceTestUtils;
import facechamp.test.EnumUtils;
import facechamp.test.UpdatableTestUtil;
import facechamp.util.Return;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceServiceTest {
  @Autowired
  private DeviceRepository deviceRepository;
  @Autowired
  private DeviceService    deviceService;

  private Instant          before;
  private Random           rand;

  @Before
  public void setUp() throws Exception {
    this.before = Instant.now();
    this.rand = new Random();
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    final long cntBefore = this.deviceRepository.count();
    final ClientTypes type = EnumUtils.random(ClientTypes.class);
    final String identifier = UUID.randomUUID().toString();
    CreateDeviceCmd cmd = new CreateDeviceCmd(type, identifier);

    // When
    final Return<DeviceDto> rv = this.deviceService.create(cmd);
    final DeviceDto dto = rv.value();

    // Then
    assertThat(dto).isNotNull()
        .extracting("type", "identifier").containsExactly(type.ordinal(), identifier);
    assertThat(dto.getId()).isGreaterThan((int) cntBefore);
    assertThat(dto.getKey()).isGreaterThan(0L);
    UpdatableTestUtil.createdAfter(dto, this.before);

    assertThat(this.deviceRepository.count()).isGreaterThan(cntBefore);
  }

  @Test
  public void testReadWithTypeAndIdentifier() throws Exception {
    // Given
    final DeviceEntity expected = DeviceTestUtils.device(this.deviceRepository);
    final ClientType type = expected.getType();
    final String identifier = expected.getIdentifier();
    ReadDeviceCmd cmd = new ReadDeviceCmd(type, identifier);

    // When
    Return<DeviceDto> rv = this.deviceService.read(cmd);
    DeviceDto actual = rv.value();

    // Then
    assertThat(actual).isNotNull()
        .isNotEqualTo(expected)
        .extracting("id", "key", "type", "identifier")
        .containsExactly(expected.getId(), expected.getKey(), type.getId(), identifier);
    UpdatableTestUtil.createdAfter(actual, this.before);
  }

  @Test
  public void testReadWithDeviceKeyNotExists() throws Exception {
    // Given
    long deviceKey;
    do {
      deviceKey = this.rand.nextLong();
    } while (0L >= deviceKey || null != this.deviceRepository.findOneByKey(deviceKey));

    // When
    Return<DeviceDto> rv = this.deviceService.read(deviceKey);
    DeviceDto dto = rv.value();

    // Then
    assertThat(dto).isNull();
  }
}
