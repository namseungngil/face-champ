package facechamp.util;

import java.time.Instant;

public class BaiscNonceGenerator implements NonceGenerator {
  private String secret;
  private String glue;

  public BaiscNonceGenerator() {
    this("", "");
  }

  public BaiscNonceGenerator(String secret, String glue) {
    super();
    this.secret = secret;
    this.glue = glue;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>NonceGenerator
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Instant timestamp() {
    return Instant.now();
  }

  @Override
  public String secret() {
    return this.secret;
  }

  @Override
  public String glue() {
    return this.glue;
  }
}
