package com.playernguyen.powreedcore.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The abstract manager list to storage data
 *
 * @param <T> the manager list
 * @see PowreedManager
 */
public abstract class PowreedManagerList<T> implements PowreedManager<T> {

    /**
     * The collection as {@link List}
     */
    private final List<T> collection;

    /**
     * The constructor with parameter can be initiated new collection with different List type
     *
     * @param collection the collection to be initiate
     */
    public PowreedManagerList(List<T> collection) {
        this.collection = collection;
    }

    /**
     * The constructor without parameter. This constructor will create new ArrayList
     */
    public PowreedManagerList() {
        this.collection = new ArrayList<>();
    }

    /**
     * The collection which storage data inside.
     * This collection should be initiated at the constructor
     *
     * @return collection which was initiated at the constructor
     * @see PowreedManager
     */
    @Override
    public Collection<T> collection() {
        return collection;
    }
}
