package io.muetsch.hashcode.qualification.solver;

import io.muetsch.hashcode.qualification.type.Intersection;
import io.muetsch.hashcode.qualification.type.Simulation;
import java.util.stream.Collectors;

public class TrivialSolver implements Solver {
  @Override
  public void solve(Simulation simulation) {

    final var usedStreets = simulation.getCars().stream()
        .flatMap(c -> c.getPath().stream())
        .collect(Collectors.toUnmodifiableSet());

    for (Intersection intersection : simulation.getIntersections()) {
      final var schedule = intersection.getSchedule();
      schedule.forEach((street, duration) -> {
        if (!usedStreets.contains(street.getName()) && schedule.size() > 1) {
          schedule.remove(street);
          final var next = schedule.keySet().iterator().next();
          if (schedule.get(next) + duration <= simulation.getDuration()) {
            schedule.put(next, schedule.get(next) + duration);
          } else {
            schedule.put(street, duration); // put back
          }
        } else {
          schedule.put(street, duration); // put back
        }
      });
    }

    simulation.setSolved(true);
  }
}
