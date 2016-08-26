/**
 *
 */
package facechamp.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import facechamp.Runner;
import facechamp.command.GetAccountCmd;
import facechamp.dto.AccountDto;
import facechamp.test.AccountTestUtils;

/**
 * @since 2016. 8. 9.
 * @author Just Burrow just.burrow@lul.kr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Runner.class, loader = SpringBootContextLoader.class)
public class AccountServiceTest {
  @Autowired
  private AccountService accountService;

  @Test
  public void testCreate() throws Exception {
    // Given
    GetAccountCmd cmd = AccountTestUtils.randomGatAccountCmd();

    // When
    AccountDto dto = this.accountService.get(cmd).value();

    // Then
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isGreaterThan(0);
    assertThat(dto.getUsername()).isNotNull();
  }
}
