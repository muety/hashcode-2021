package io.muetsch.hashcode.qualification.type;

import java.util.List;

public class Car {

  public Car(int id) {
    this.id = id;
  }

  private int id;

  private List<String> path;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<String> getPath() {
    return path;
  }

  public void setPath(List<String> path) {
    this.path = path;
  }
}
