package facechamp.service;

import java.time.Instant;
import java.util.Random;

abstract class AbstractServiceTest {
  protected Instant before;
  protected Random  random;

  protected void init() {
    this.before = Instant.now();
    this.random = new Random();
  }
}
