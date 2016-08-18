/**
 *
 */
package facechamp.domain;

/**
 * 토너먼트 참가 선수 정보.
 * 모든 계정이 참가하는 것은 아니기 때문에, {@link Account}와 분리한 별도의 자료형을 가진다.
 *
 * @since 2016. 8. 8.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface Player extends Taggable {
  /**
   * @return
   * @since 2016. 8. 8.
   */
  public int getId();

  /**
   * 소유 계정.
   *
   * @return
   * @since 2016. 8. 8.
   */
  public Account getOwner();
}
