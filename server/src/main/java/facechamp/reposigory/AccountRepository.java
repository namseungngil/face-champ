package facechamp.reposigory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import facechamp.domain.entity.AccountEntity;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
  /**
   * @param name
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public AccountEntity findOneByName(String name);
}
