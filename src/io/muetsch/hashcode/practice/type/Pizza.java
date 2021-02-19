package io.muetsch.hashcode.practice.type;

import java.util.BitSet;
import java.util.Objects;

public class Pizza {
    private long id;
    private boolean delivered;
    private BitSet ingredients;

    public Pizza(long id) {
        this.id = id;
    }

    public Pizza(long id, BitSet ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BitSet getIngredients() {
        return ingredients;
    }

    public void setIngredients(BitSet ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public int getNumIngredients() {
        return ingredients.cardinality();
    }

    public long diffLenWith(BitSet ingredients) {
        final var intersect = new BitSet(this.ingredients.size());
        intersect.or(this.ingredients);
        intersect.or(ingredients);
        return intersect.cardinality();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return id == pizza.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
