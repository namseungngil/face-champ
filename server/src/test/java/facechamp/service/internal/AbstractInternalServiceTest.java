package facechamp.service.internal;

import java.time.Instant;
import java.util.Random;

abstract class AbstractInternalServiceTest {
  protected Instant before;
  protected Random  random;

  protected void init() {
    this.before = Instant.now();
    this.random = new Random();
  }
}
