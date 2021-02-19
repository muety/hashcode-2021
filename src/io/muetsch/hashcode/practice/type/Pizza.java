package io.muetsch.hashcode.practice.type;

import io.muetsch.hashcode.practice.util.LRUCache;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Pizza {
    private long id;
    private boolean delivered;
    private Set<String> ingredients;

    private final LRUCache diffCache = new LRUCache(2 << 5);

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

    public long diffLenWith(Team needyTeam) {
        final var hc = needyTeam.hashCode();
        if (diffCache.get(hc) != -1L) {
            return diffCache.get(hc);
        }

        final var count = Stream.concat(this.ingredients.stream(), needyTeam.getIngredients().stream())
                .distinct()
                .count();

        diffCache.put(hc, count);
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return id == pizza.id &&
                delivered == pizza.delivered;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, delivered);
    }
}
