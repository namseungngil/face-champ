package facechamp.domain;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public interface Device extends Updatable {
  /**
   * 등록 기기의 일련번호.
   * (대체키)
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
   * @since 2016. 8. 28.
   */
  public Account getOwner();

  /**
   * @param account
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public void setOwner(Account account);

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public ClientType getType();

  /**
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public String getIdentifier();
}
