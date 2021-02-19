package io.muetsch.hashcode.practice.solver;

import io.muetsch.hashcode.practice.type.Pizza;
import io.muetsch.hashcode.practice.type.Problem;
import io.muetsch.hashcode.practice.type.Team;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GreedySolver implements Solver {
    @Override
    public void solve(Problem problem) {
        final var pizzas = problem.getPizzas().stream()
                .sorted(Comparator.comparing(Pizza::getNumIngredients))
                .collect(Collectors.toCollection(LinkedList::new));
        final var teams = problem.getTeams().stream()
                .sorted(Comparator.comparing(Team::getMembers))
                .collect(Collectors.toUnmodifiableList());

        int i = 0;

        for (final var t : teams) {
            if (i++ % 10 == 0) {
                System.out.printf("Processing team %d of %d ...\n", i, teams.size());
            }

            if (!pizzas.isEmpty()) {
                final var firstPizza = pizzas.remove();
                t.addPizza(firstPizza);

                while (!t.isValid()) {
                    final var nextPizza = findBestCandidate(t, pizzas);
                    nextPizza.ifPresentOrElse(
                            p -> {
                                t.addPizza(p);
                                pizzas.remove(p);
                                p.setDelivered(true);
                            },
                            () -> {
                                // Team can't be satisfied, roll back and deliver nothing
                                t.setPizzas(List.of());
                                pizzas.push(firstPizza);
                            }
                    );
                }

                firstPizza.setDelivered(true);
            }
        }
    }

    private Optional<Pizza> findBestCandidate(Team teamWithPizzas, List<Pizza> pizzas) {
        assert teamWithPizzas.doesGetPizza();
        return pizzas.parallelStream()
                .filter(p -> !p.isDelivered())
                .max(Comparator.comparing(p -> p.diffLenWith(teamWithPizzas.getIngredients())));
    }
}
