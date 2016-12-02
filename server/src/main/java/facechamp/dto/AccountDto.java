/**
 *
 */
package facechamp.dto;

import java.time.Instant;

import lombok.Data;

/**
 * @since 2016. 7. 30.
 * @author Just Burrow just.burrow@lul.kr
 */
@Data
public class AccountDto {
  private int     id;
  private String  username;
  private Instant create;
  private Instant update;
}
