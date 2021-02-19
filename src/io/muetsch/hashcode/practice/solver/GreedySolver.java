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
    private final Config config;

    public GreedySolver(Config config) {
        this.config = config;
    }

    @Override
    public void solve(Problem problem) {
        var pizzaSorter = Comparator.comparing(Pizza::getNumIngredients);
        var teamSorter = Comparator.comparing(Team::getMembers);

        if (config.largePizzasFirst) pizzaSorter = pizzaSorter.reversed();
        if (config.largeTeamsFirst) teamSorter = teamSorter.reversed();

        final var pizzas = problem.getPizzas().stream()
                .sorted(pizzaSorter)
                .collect(Collectors.toCollection(LinkedList::new));

        final var teams = problem.getTeams().stream()
                .sorted(teamSorter)
                .collect(Collectors.toUnmodifiableList());

        int i = 0;

        for (final var t : teams) {
            if (i++ % 10 == 0) {
                System.out.printf("Processing team %d of %d ...\n", i, teams.size());
            }

            if (!pizzas.isEmpty()) {
                final var firstPizza = pizzas.remove();
                t.addPizza(firstPizza);
                firstPizza.setDelivered(true);

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
                                firstPizza.setDelivered(false);
                                pizzas.push(firstPizza);
                            }
                    );
                }
            }
        }
    }

    private Optional<Pizza> findBestCandidate(Team teamWithPizzas, List<Pizza> pizzas) {
        assert teamWithPizzas.doesGetPizza();
        return pizzas.parallelStream()
                .unordered()
                .filter(p -> !p.isDelivered())
                .max(Comparator.comparing(p -> p.diffLenWith(teamWithPizzas.getIngredients())));
    }

    public static class Config {
        public Config(boolean largeTeamsFirst, boolean largePizzasFirst) {
            this.largeTeamsFirst = largeTeamsFirst;
            this.largePizzasFirst = largePizzasFirst;
        }

        public boolean largeTeamsFirst;
        public boolean largePizzasFirst;
    }
}
