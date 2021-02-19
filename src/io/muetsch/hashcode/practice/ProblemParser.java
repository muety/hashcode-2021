package io.muetsch.hashcode.practice;

import io.muetsch.hashcode.practice.type.Problem;
import io.muetsch.hashcode.practice.type.Pizza;
import io.muetsch.hashcode.practice.type.Team;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemParser {
    private int pos;
    private final Problem result = new Problem();

    public void feedLine(String line) {
        if (pos == 0) {
            // Parse first meta data line
            final var nums = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toUnmodifiableList());

            assert nums.size() == 4;

            result.setNumPizzas(nums.get(0));
            result.setNumTwoTeams(nums.get(1));
            result.setNumThreeTeams(nums.get(2));
            result.setNumFourTeams(nums.get(3));

            IntStream.range(0, result.getNumTwoTeams())
                    .mapToObj(i -> new Team(i, 2))
                    .forEach(result::addTeam);
            IntStream.range(0, result.getNumThreeTeams())
                    .mapToObj(i -> new Team(i, 3))
                    .forEach(result::addTeam);
            IntStream.range(0, result.getNumFourTeams())
                    .mapToObj(i -> new Team(i, 4))
                    .forEach(result::addTeam);
        } else {
            // Parse pizza definition
            final var ingredients = Arrays.stream(line.split(" "))
                    .skip(1)
                    .collect(Collectors.toUnmodifiableSet());
            result.addPizza(new Pizza(pos - 1, ingredients));
        }

        pos++;
    }

    public Problem get() {
        return result;
    }

}
