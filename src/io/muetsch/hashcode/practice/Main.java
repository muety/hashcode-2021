package io.muetsch.hashcode.practice;

import io.muetsch.hashcode.practice.solver.GreedySolver;
import io.muetsch.hashcode.practice.solver.Solver;
import io.muetsch.hashcode.practice.type.Problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Parsing input ...\n");
        final var scanner = new Scanner(System.in);
        final var parser = new ProblemParser();
        while (scanner.hasNext()) {
            parser.feedLine(scanner.nextLine());
        }
        final var problem = parser.get();
        System.out.println(problem.summarize());

        System.out.println("Solving ...");
        final var start = Instant.now();
        final var solver = new GreedySolver();
        problem.solve(solver);
        assert problem.isSolved();
        final var end = Instant.now();
        System.out.printf("Solved after %s.\n\n", Duration.between(start, end).toString());

        System.out.println("Validating result ...\n");
        assert problem.isValid();

        System.out.printf("Solved. Score is %.0f.\n", problem.getScore());

        dump(problem);
    }

    private static void dump(Problem problem) {
        assert problem.isSolved();

        final var now = LocalDateTime.now();
        final var fileName = "solution_%s.txt".formatted(
                now.format(DateTimeFormatter.ofPattern("HH-mm-ss"))
        );

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
}
