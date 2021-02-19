package io.muetsch.hashcode.practice.type;

import io.muetsch.hashcode.practice.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Problem {

    private boolean solved;
    private int numPizzas;
    private int numTwoTeams;
    private int numThreeTeams;
    private int numFourTeams;
    private List<Team> teams = new ArrayList<>();
    private List<Pizza> pizzas = new ArrayList<>();

    public int getNumPizzas() {
        return numPizzas;
    }

    public void setNumPizzas(int numPizzas) {
        this.numPizzas = numPizzas;
    }

    public int getNumTwoTeams() {
        return numTwoTeams;
    }

    public void setNumTwoTeams(int numTwoTeams) {
        this.numTwoTeams = numTwoTeams;
    }

    public int getNumThreeTeams() {
        return numThreeTeams;
    }

    public void setNumThreeTeams(int numThreeTeams) {
        this.numThreeTeams = numThreeTeams;
    }

    public int getNumFourTeams() {
        return numFourTeams;
    }

    public void setNumFourTeams(int numFourTeams) {
        this.numFourTeams = numFourTeams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public boolean isSolved() {
        return solved;
    }

    public void addPizza(Pizza pizza) {
        this.getPizzas().add(pizza);
    }

    public void addTeam(Team team) {
        this.getTeams().add(team);
    }

    public void solve(Solver solver) {
        solver.solve(this);
        solved = true;
    }

    public double getScore() {
        return teams.stream()
                .mapToDouble(Team::getScore)
                .sum();
    }

    public boolean isValid() {
        // Every team satisfied (all or nothing)
        if (!teams.stream().allMatch(Team::isValid)) {
            System.err.println("At least one team is invalidly satisfied with Pizza");
            return false;
        }
        final var deliveredPizzaIds = teams.stream()
                .flatMap(t -> t.getPizzas().stream())
                .map(Pizza::getId)
                .collect(Collectors.toList());
        final var uniqueDeliveredPizzas = Set.of(deliveredPizzaIds);
        if (deliveredPizzaIds.size() != uniqueDeliveredPizzas.size()) {
            System.err.printf(
                    "At least one pizza was delivered twice (%d / %d)\n",
                    deliveredPizzaIds.size(),
                    uniqueDeliveredPizzas.size()
            );
            return false;
        }
        return true;
    }

    public String summarize() {
        return """
                ######
                # Pizzas: %d
                # Teams of 2: %d
                # Teams of 3: %d
                # Teams of 4: %d
                # Different ingredients: %d
                ######
                """.formatted(
                numPizzas,
                numTwoTeams,
                numThreeTeams,
                numFourTeams,
                pizzas.stream()
                        .map(Pizza::getIngredients)
                        .distinct()
                        .count()
        );
    }

    @Override
    public String toString() {
        if (!solved) return "Unsolved problem.";

        final var sb = new StringBuilder();
        final var numTeamsWithPizza = teams.stream()
                .filter(Team::doesGetPizza)
                .peek(t -> sb.append(t.getMembers()))
                .peek(t -> sb.append(" "))
                .peek(t -> sb.append(
                        t.getPizzas().stream()
                                .map(p -> Long.toString(p.getId()))
                                .collect(Collectors.joining(" "))
                ))
                .peek(t -> sb.append("\n"))
                .count();

        return "%d\n%s".formatted(numTeamsWithPizza, sb.toString());
    }

}
