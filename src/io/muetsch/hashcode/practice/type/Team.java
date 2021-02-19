package io.muetsch.hashcode.practice.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Team {
    private long id;
    private int members;
    private List<Pizza> pizzas = new ArrayList<>();

    public Team(long id, int members) {
        this.id = id;
        this.members = members;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public boolean isValid() {
        return pizzas.size() == 0 || doesGetPizza();
    }

    public boolean doesGetPizza() {
        return pizzas.size() == members;
    }

    public Set<String> getIngredients() {
        return pizzas.stream()
                .flatMap(p -> p.getIngredients().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    public double getScore() {
        return Math.pow(getIngredients().size(), 2);
    }
}
