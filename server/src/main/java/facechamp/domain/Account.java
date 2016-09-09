package facechamp.domain;

import java.net.URL;

public interface Account extends Updatable {
  /**
   * @author Just Burrow
   * @since 2016. 9. 6.
   */
  public String NAME_PATTERN = "\\S.{0,43}\\S";

  public int getId();

  public String getName();

  public void setName(String name);

  public String getBio();

  public void setBio(String bio);

  public URL getPortrait();

  public void setPortrait(URL portraitUrl);
}
