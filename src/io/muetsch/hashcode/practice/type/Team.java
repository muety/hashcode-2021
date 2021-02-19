package io.muetsch.hashcode.practice.type;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Team {
    public static int totalIngredients; // a bit hacky, but works

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

    public BitSet getIngredients() {
        final var combined = new BitSet(totalIngredients);
        pizzas.forEach(p -> combined.or(p.getIngredients()));
        return combined;
    }

    public double getScore() {
        return Math.pow(getIngredients().cardinality(), 2);
    }
}
