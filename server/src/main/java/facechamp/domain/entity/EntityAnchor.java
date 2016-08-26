package facechamp.domain.entity;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
public abstract class EntityAnchor {
  public static final Package PACKAGE      = EntityAnchor.class.getPackage();

  public static final String  PACKAGE_NAME = EntityAnchor.PACKAGE.getName();

  private EntityAnchor() {
    throw new UnsupportedOperationException();
  }
}
