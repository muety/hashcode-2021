package io.muetsch.hashcode.qualification;

import io.muetsch.hashcode.qualification.type.Car;
import io.muetsch.hashcode.qualification.type.Intersection;
import io.muetsch.hashcode.qualification.type.Simulation;
import io.muetsch.hashcode.qualification.type.Street;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemParser {

  private int pos;
  private final List<String> lines;

  private int numCars;
  private int numIntersections;
  private int numStreets;
  private final Simulation simulation;

  public ProblemParser() {
    lines = new LinkedList<>();
    simulation = new Simulation();
  }

  public void feedLine(String line) {
    lines.add(line);
    processLine(line);
  }

  private void processLine(String line) {
    if (pos == 0) {
      final var nums = Arrays.stream(line.split(" "))
          .map(Integer::parseInt)
          .collect(Collectors.toUnmodifiableList());

      assert nums.size() == 5;

      simulation.setDuration(nums.get(0));
      numIntersections = nums.get(1);
      numStreets = nums.get(2);
      numCars = nums.get(3);
      simulation.setReward(nums.get(4));
    } else if (pos > 0 && pos <= numStreets) {
      final var parts = line.split(" ");

      final var iId1 = Integer.parseInt(parts[0]);
      final var iId2 = Integer.parseInt(parts[1]);

      final var i1 = simulation.getIntersections().stream()
          .filter(i -> i.getId() == iId1)
          .findFirst()
          .orElse(new Intersection(iId1));
      final var i2 = simulation.getIntersections().stream()
          .filter(i -> i.getId() == iId2)
          .findFirst()
          .orElse(new Intersection(iId2));

      final var street = new Street(parts[2], Integer.parseInt(parts[3]));
      i1.addOut(street);
      i2.addIn(street);

      simulation.getIntersections().add(i1);
      simulation.getIntersections().add(i2);
    } else {
      final var parts = line.split(" ");
      final var car = new Car(Integer.parseInt(parts[0]));
      car.setPath(Arrays.stream(parts)
          .skip(1)
          .collect(Collectors.toList()));
      simulation.getCars().add(car);
    }

    pos++;
  }

  public Simulation get() {
    postprocess();
    return simulation;
  }

  private void postprocess() {
  }

}
