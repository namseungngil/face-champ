/**
 *
 */
package facechamp.domain.entity;

import facechamp.domain.ClientType;

/**
 * 클라이언트 프로그램의 종류.
 *
 * @since 2016. 7. 25.
 * @author Just Burrow just.burrow@lul.kr
 */
public enum ClientTypes implements ClientType {
  WIN,
  IOS,
  ANDROID;

  private static ClientTypes[] valArray = ClientTypes.values();

  /**
   * @param ordinal
   * @return
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  public static ClientTypes valueOf(int ordinal) {
    return valArray[ordinal];
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>ClientType
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @Override
  public int getId() {
    return this.ordinal();
  }

  /*
   * (non-Javadoc)
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  @Override
  public String getName() {
    return this.name();
  }
}
