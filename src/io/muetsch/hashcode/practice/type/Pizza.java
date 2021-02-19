package io.muetsch.hashcode.practice.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Pizza {
    private long id;
    private boolean delivered;
    private Set<String> ingredients;

    public Pizza(long id, Set<String> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
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

    public long diffLenWith(Set<String> ingredients) {
        return Stream.concat(this.ingredients.stream(), ingredients.stream())
                .distinct()
                .count();
    }
}
