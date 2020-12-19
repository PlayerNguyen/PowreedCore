package com.playernguyen.powreedcore.managers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The container collection will be as Set
 *
 * @param <T> the data type of that
 *            collection
 */
public abstract class PowreedManagerSet<T> implements PowreedManager<T> {

    private final Set<T> collection;

    public PowreedManagerSet(Set<T> collection) {
        this.collection = collection;
    }

    public PowreedManagerSet() {
        this.collection = new HashSet<>();
    }

    /**
     * @see PowreedManager#collection()
     */
    @Override
    public Collection<T> collection() {
        return collection;
    }

}
