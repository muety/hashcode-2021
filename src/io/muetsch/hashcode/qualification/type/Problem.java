package io.muetsch.hashcode.qualification.type;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Problem {

  private boolean solved;
  private int duration;
  private int reward;
  private Set<Intersection> intersections;
  private Set<Car> cars;

  public Problem() {
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

  public String summarize() {
    final var streets = intersections.stream()
        .flatMap(i -> i.getIn().stream())
        .collect(Collectors.toUnmodifiableSet());

    final var usedStreets = cars.stream()
        .flatMap(i -> i.getPath().stream())
        .collect(Collectors.toUnmodifiableSet());

    return """
        ######
        Duration: %d
        Reward: %d
        # Cars: %d
        # Intersections: %d
        # Streets: %d (%d [%.1f %%] used)
        ######
        """.formatted(
        duration,
        reward,
        cars.size(),
        intersections.size(),
        streets.size(),
        usedStreets.size(),
        ((float) usedStreets.size()) / ((float) streets.size()) * 100
    );
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
