package facechamp.domain;

import java.net.URL;

public interface Account extends Updatable {
  public int getId();

  public String getName();

  public void setName(String name);

  public String getBio();

  public void setBio(String bio);

  public URL getPortrait();

  public void setPortrait(URL portraitUrl);
}
