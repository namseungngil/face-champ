/**
 *
 */
package facechamp.reposigory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import facechamp.domain.entity.TagEntity;

/**
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, String> {
}
