package io.muetsch.hashcode.practice.type;

import java.util.Set;
import java.util.stream.Stream;

public class Pizza {
    private long id;
    private boolean delivered;
    private Set<Integer> ingredients;

    public Pizza(long id, Set<Integer> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public int getNumIngredients() {
        return ingredients.size();
    }

    public long diffLenWith(Set<Integer> ingredients) {
        return Stream.concat(this.ingredients.stream(), ingredients.stream())
                .unordered()
                .distinct()
                .count();
    }
}
