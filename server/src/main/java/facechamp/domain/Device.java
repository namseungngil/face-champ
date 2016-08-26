package facechamp.domain;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public interface Device extends Updatable {
  /**
   * 등록 기기의 일련번호.
   *
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public int getId();

  /**
   * 랜덤하게 지정한 기기 고유번호.
   *
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public long getKey();

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public ClientType getType();

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 26.
   */
  public String getIdentifier();
}
