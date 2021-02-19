package io.muetsch.hashcode.practice;

import io.muetsch.hashcode.practice.solver.GreedySolver;
import io.muetsch.hashcode.practice.type.Problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
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

        final var configs = configCombinations();

        for (int i = 0; i < configs.length; i++) {
            final var problem = i == 0 ? parser.get() : parser.replayed().get();
            System.out.println(problem.summarize());

            System.out.printf("Solving (%d of %d) ...\n", i + 1, configs.length);
            final var start = Instant.now();
            final var solver = new GreedySolver(configs[i]);
            problem.solve(solver);
            assert problem.isSolved();
            final var end = Instant.now();
            System.out.printf("Solved after %s.\n\n", Duration.between(start, end).toString());

            System.out.println("Validating result ...\n");
            assert problem.isValid();

            System.out.printf("Solved. Score is %.0f.\n\n", problem.getScore());

            dump(args[0], problem);
        }
    }

    private static void dump(String prefix, Problem problem) {
        assert problem.isSolved();

        final var fileName = "solution_%s_%.0f.txt".formatted(prefix, problem.getScore());
        try {
            Files.write(
                    Path.of(fileName),
                    problem.toString().getBytes(),
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GreedySolver.Config[] configCombinations() {
        return new GreedySolver.Config[]{
                new GreedySolver.Config(true, true),
                new GreedySolver.Config(true, false),
                new GreedySolver.Config(false, false),
                new GreedySolver.Config(false, true),
        };
    }
}
