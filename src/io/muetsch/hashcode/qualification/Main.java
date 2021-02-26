package io.muetsch.hashcode.qualification;

import io.muetsch.hashcode.qualification.solver.TrivialSolver;
import io.muetsch.hashcode.qualification.type.Problem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Main {

  // TODO: Implement simulation and score function
  // TODO: More advanced optimization

  public static void main(String[] args) {
    assert args.length == 1;

    System.out.println("Parsing input ...");
    final var scanner = new Scanner(System.in);
    final var parser = new ProblemParser();
    while (scanner.hasNext()) {
      parser.feedLine(scanner.nextLine());
    }

    final var problem = parser.get();
    System.out.println(problem.summarize());

    System.out.println("Solving ...");
    final var start = Instant.now();
    final var solver = new TrivialSolver();
    solver.solve(problem);
    assert problem.isSolved();
    final var end = Instant.now();
    System.out.printf("Solved after %s.\n\n", Duration.between(start, end).toString());

    System.out.println("Writing output ...");
    dump(args[0], problem);
  }

  private static void dump(String prefix, Problem simulation) {
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
