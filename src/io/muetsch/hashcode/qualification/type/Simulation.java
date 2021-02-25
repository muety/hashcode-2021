package io.muetsch.hashcode.qualification.type;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.w3c.dom.ls.LSOutput;

public class Simulation {

  private boolean solved;
  private int duration;
  private int reward;
  private Set<Intersection> intersections;
  private Set<Car> cars;

  public Simulation() {
    this.cars = new HashSet<>();
    this.intersections = new HashSet<>();
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Set<Intersection> getIntersections() {
    return intersections;
  }

  public void setIntersections(Set<Intersection> intersections) {
    this.intersections = intersections;
  }

  public int getReward() {
    return reward;
  }

  public void setReward(int reward) {
    this.reward = reward;
  }

  public boolean isSolved() {
    return solved;
  }

  public void setSolved(boolean solved) {
    this.solved = solved;
  }

  public Set<Car> getCars() {
    return cars;
  }

  public void setCars(Set<Car> cars) {
    this.cars = cars;
  }

  public long getScore() {
    return 0L;
  }

  @Override
  public String toString() {
    final var sb = new StringBuilder();

    final var scheduledIntersections = getIntersections().stream()
        .filter(i -> !i.getSchedule().isEmpty())
        .collect(Collectors.toUnmodifiableSet());

    sb.append(scheduledIntersections.size());
    sb.append(System.lineSeparator());

    scheduledIntersections.forEach(i -> {
      sb.append(i.getId());
      sb.append(System.lineSeparator());
      sb.append(i.getSchedule().size());
      sb.append(System.lineSeparator());

      i.getSchedule().forEach((street, duration) -> {
        sb.append(street.getName());
        sb.append(" ");
        sb.append(duration);
        sb.append(System.lineSeparator());
      });
    });

    return sb.toString();
  }
}
