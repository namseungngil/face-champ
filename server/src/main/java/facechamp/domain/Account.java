/**
 *
 */
package facechamp.domain;

import java.time.Instant;

/**
 * 클라이언트 타입과 HW의 ID를 인식 키로 한다.
 *
 * @since 2016. 7. 25.
 * @author Just Burrow just.burrow@lul.kr
 */
public interface Account {
  public int getId();

  public HardwareIdentifier getDevice();

  public String getUsername();

  public void setUsername(String username);

  public Instant getCreate();

  public Instant getUpdate();
}
