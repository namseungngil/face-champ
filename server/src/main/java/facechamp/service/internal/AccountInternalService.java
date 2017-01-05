package facechamp.service.internal;

import org.springframework.transaction.annotation.Transactional;

import facechamp.domain.Account;
import facechamp.service.ctx.CreateAccountCtx;

/**
 * @author Just Burrow
 * @since 2016. 8. 28.
 */
@Transactional
public interface AccountInternalService {
  /**
   * @param ctx
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Account create(CreateAccountCtx ctx);

  /**
   * @param id
   * @return
   * @author Just Burrow
   * @since 2016. 8. 28.
   */
  public Account read(int id);
}
