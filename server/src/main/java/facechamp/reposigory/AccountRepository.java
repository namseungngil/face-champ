/**
 *
 */
package facechamp.reposigory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import facechamp.domain.HardwareIdentifier;
import facechamp.domain.entity.AccountEntity;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

  /**
   * @param device
   * @return
   * @since 2016. 7. 30.
   */
  public AccountEntity findOneByDevice(HardwareIdentifier device);

  /**
   * @param username
   * @return
   * @since 2016. 7. 30.
   */
  public long countByUsername(String username);
}
