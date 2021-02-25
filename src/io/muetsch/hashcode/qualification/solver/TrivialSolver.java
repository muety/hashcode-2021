package io.muetsch.hashcode.qualification.solver;

import io.muetsch.hashcode.qualification.type.Intersection;
import io.muetsch.hashcode.qualification.type.Simulation;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TrivialSolver implements Solver {

  @Override
  public void solve(Simulation simulation) {
    final var threshold = 1;

    final var usedStreets = simulation.getCars().stream()
        .flatMap(c -> c.getPath().stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Intersection intersection : simulation.getIntersections()) {
      final var schedule = intersection.getSchedule();
      schedule.forEach((street, duration) -> {
        if ((!usedStreets.containsKey(street.getName()) || usedStreets.get(street.getName()) < threshold) && schedule.size() > threshold) {
          schedule.remove(street);
          final var next = schedule.entrySet().stream().max(Comparator.comparing(o -> usedStreets.getOrDefault(o.getKey().getName(), 0L))).get().getKey();
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
