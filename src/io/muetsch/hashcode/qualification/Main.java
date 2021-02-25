package io.muetsch.hashcode.qualification;

import io.muetsch.hashcode.practice.type.Problem;
import io.muetsch.hashcode.qualification.solver.TrivialSolver;
import io.muetsch.hashcode.qualification.type.Simulation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    assert args.length == 1;

    System.out.println("Parsing input ...\n");
    final var scanner = new Scanner(System.in);
    final var parser = new ProblemParser();
    while (scanner.hasNext()) {
      parser.feedLine(scanner.nextLine());
    }

    final var simulation = parser.get();

    System.out.println("Solving ...");
    final var solver = new TrivialSolver();
    solver.solve(simulation);

    System.out.println("Writing output ...");
    dump(args[0], simulation);
  }

  private static void dump(String prefix, Simulation simulation) {
    assert simulation.isSolved();

    final var fileName = "solution_%s_%d.txt".formatted(prefix, simulation.getScore());
    try {
      Files.write(
          Path.of(fileName),
          simulation.toString().getBytes(),
          StandardOpenOption.CREATE
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
