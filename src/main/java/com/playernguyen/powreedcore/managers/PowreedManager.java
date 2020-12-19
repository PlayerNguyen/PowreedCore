package com.playernguyen.powreedcore.managers;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * The interface class to manger object. The data will be in system memory.
 *
 * @param <T> the type of object to store
 */
public interface PowreedManager<T> extends Iterable<T>{

    /**
     * The collection which storage data inside. This collection should be initiated at the constructor
     *
     * @return collection which was initiated at the constructor
     */
    Collection<T> collection();

    /**
     * Iterator method which inherited from {@link #collection()} class
     * @return the {@link #collection()#iterator()} method
     */
    @NotNull @Override
    default Iterator<T> iterator() {
        return collection().iterator();
    }
}
