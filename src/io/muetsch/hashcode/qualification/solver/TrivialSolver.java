package io.muetsch.hashcode.qualification.solver;

import io.muetsch.hashcode.qualification.type.Problem;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TrivialSolver implements Solver {
  private static final int THRESHOLD = 1;
  private static final int DEFAULT_DURATION = 1;

  @Override
  public void solve(Problem problem) {
    problem.getIntersections().forEach(intersection -> {
      intersection.setSchedule(intersection.getIn().stream()
          .collect(Collectors.toMap(
              Function.identity(),
              ok -> DEFAULT_DURATION,
              (o1, o2) -> o1,
              ConcurrentHashMap::new
          )));
    });

    final var usedStreets = problem.getCars().stream()
        .flatMap(c -> c.getPath().stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // "Optimization" #1: Remove unused streets
    problem.getIntersections().forEach(intersection -> {
      final var schedule = intersection.getSchedule();
      schedule.forEach((street, duration) -> {
        if ((!usedStreets.containsKey(street.getName()) || usedStreets.get(street.getName()) < THRESHOLD) && schedule.size() > THRESHOLD) {
          final var next = schedule.keySet().stream()
              .filter(s -> !Objects.equals(s, street))
              .max(Comparator.comparing(o -> usedStreets.getOrDefault(o.getName(), 0L))).get();
          if (schedule.get(next) + duration <= problem.getDuration()) {
            schedule.put(next, schedule.get(next) + duration);
            schedule.remove(street);
          }
        }
      });
    });

    problem.setSolved(true);
  }
}
