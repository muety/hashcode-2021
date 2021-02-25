package io.muetsch.hashcode.qualification.type;

public class Street {

  public Street(String name, int duration) {
    this.duration = duration;
    this.name = name;
  }

  private int duration;

  private String name;

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Street street = (Street) o;

    if (duration != street.duration) {
      return false;
    }
    return name != null ? name.equals(street.name) : street.name == null;
  }

  @Override
  public int hashCode() {
    int result = duration;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
