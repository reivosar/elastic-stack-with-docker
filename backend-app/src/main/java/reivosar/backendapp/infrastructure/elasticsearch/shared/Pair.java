package reivosar.backendapp.infrastructure.elasticsearch.shared;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Pair {

    private final Object key;
    private final Collection<Object> values;

    public static Pair of(final Object key, final Object... values) {
        return new Pair(key, Arrays.asList(values));
    }

    private Pair(final Object key, final Collection<Object> values) {
        this.key = key;
        this.values = values;
    }

    @Override
    public boolean equals(final Object obj) {
        return Objects.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    public Object key() {
        return this.key;
    }

    public Collection<Object> values() {
        return this.values;
    }

    public boolean hasValues() {
        return (this.values().size() > 0);
    }

    public int valueSize() {
        return this.hasValues() ? this.values().size() : 0;
    }

    public Object firstValue() {
        return this.get(0);
    }

    public Object get(final int index) {
        if (this.valueSize() >= index) {
            return this.values().toArray()[index];
        }
        return null;
    }
}