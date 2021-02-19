package io.muetsch.hashcode.practice.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Team {
    private long id;
    private int members;
    private List<Pizza> pizzas = new ArrayList<>();

    private Set<Integer> ingredientsCache = new HashSet<>();
    private boolean ingredientsDirty = true;

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
        return pizzas.stream().collect(Collectors.toUnmodifiableList());
    }

    public void setPizzas(List<Pizza> pizzas) {
        setDirty();
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza) {
        setDirty();
        pizzas.add(pizza);
    }

    public boolean isValid() {
        return pizzas.size() == 0 || doesGetPizza();
    }

    public boolean doesGetPizza() {
        return pizzas.size() == members;
    }

    public Set<Integer> getIngredients() {
        if (ingredientsDirty) {
            ingredientsCache = pizzas.stream()
                    .flatMap(p -> p.getIngredients().stream())
                    .collect(Collectors.toUnmodifiableSet());
            ingredientsDirty = false;
        }
        return ingredientsCache;
    }

    public double getScore() {
        return Math.pow(getIngredients().size(), 2);
    }

    private void setDirty() {
        ingredientsDirty = true;
    }
}
