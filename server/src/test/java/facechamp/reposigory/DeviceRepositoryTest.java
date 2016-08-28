package facechamp.reposigory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import facechamp.domain.ClientType;
import facechamp.domain.entity.DeviceEntity;
import facechamp.test.DeviceTestUtils;
import facechamp.test.UpdatableTestUtil;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 * @see https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4#spring-boot-1-4-simplifications
 * @see https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4#testing-the-jpa-slice
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DeviceRepositoryTest {
  @Autowired
  private EntityManager    entityManager;
  @Autowired
  private DeviceRepository deviceRepository;

  private Instant          before;

  @Before
  public void setUp() {
    this.before = Instant.now();
  }

  @Test
  public void testFindAll() throws Exception {
    // Given
    final DeviceEntity expected = DeviceTestUtils.device();
    final int id = expected.getId();
    final long key = expected.getKey();
    final ClientType type = expected.getType();
    final String identifier = expected.getIdentifier();
    this.entityManager.persist(expected);

    // When
    DeviceEntity actual = this.deviceRepository.findOneByKey(expected.getKey());

    // Then
    assertThat(actual).isNotNull()
        .extracting("type", "identifier").containsExactly(type, identifier);
    assertThat(actual.getId()).isGreaterThan(id);
    if (expected != actual) {
      assertThat(actual).isNotSameAs(expected);
    }
    assertThat(actual.getId()).isGreaterThan(0)
        .isNotEqualTo(id);
    assertThat(actual.getKey()).isGreaterThan(0L)
        .isEqualTo(key);
    UpdatableTestUtil.createdAfter(actual, this.before);
  }
}
