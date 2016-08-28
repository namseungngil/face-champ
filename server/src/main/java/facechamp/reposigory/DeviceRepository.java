package facechamp.reposigory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import facechamp.domain.entity.ClientTypes;
import facechamp.domain.entity.DeviceEntity;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
  /**
   * @param type
   * @param identifier
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public DeviceEntity findOneByTypeAndIdentifier(ClientTypes type, String identifier);

  /**
   * @param key
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public DeviceEntity findOneByKey(long key);
}
