package io.muetsch.hashcode.qualification.type;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Intersection {

  private int id;
  private final Set<Street> in;
  private final Set<Street> out;
  private Map<Street, Integer> schedule;

  public Intersection(int id) {
    this.id = id;
    this.in = new HashSet<>();
    this.out = new HashSet<>();
    this.schedule = new ConcurrentHashMap<>();
  }

  public Set<Street> getIn() {
    return in.stream().collect(Collectors.toUnmodifiableSet());
  }

  public Set<Street> getOut() {
    return out.stream().collect(Collectors.toUnmodifiableSet());
  }

  public void addIn(Street street) {
    in.add(street);
  }

  public void addOut(Street street) {
    out.add(street);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Map<Street, Integer> getSchedule() {
    return schedule;
  }

  public void setSchedule(Map<Street, Integer> schedule) {
    this.schedule = schedule;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Intersection that = (Intersection) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
