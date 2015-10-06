package com.epam.benchmark.util;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copied from Green dao for test purposes. Must be optimized somehow
 *
 * @author Egor Makovsky
 */
public class CursorList<E> implements CloseableList<E> {
    public interface CursorToEntityConverter<E> {
        E convert(Cursor cursor);
    }

    protected class LazyIterator implements CloseableListIterator<E> {
        private int index;
        private final boolean closeWhenDone;

        public LazyIterator(int startLocation, boolean closeWhenDone) {
            index = startLocation;
            this.closeWhenDone = closeWhenDone;
        }

        @Override
        public void add(E object) {
            throw new UnsupportedOperationException();
        }

        @Override
        /** FIXME: before hasPrevious(), next() must be called. */
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        /** FIXME: before previous(), next() must be called. */
        public E previous() {
            if (index <= 0) {
                throw new NoSuchElementException();
            }
            index--;
            E entity = get(index);
            // if (index == size && closeWhenDone) {
            // close();
            // }
            return entity;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void set(E object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            E entity = get(index);
            index++;
            if (index == size && closeWhenDone) {
                close();
            }
            return entity;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
            CursorList.this.close();
        }

    }

    private final CursorToEntityConverter<E> cursorConverter;
    private final Cursor cursor;
    private final List<E> entities;
    private final int size;
    private final ReentrantLock lock;
    private volatile int loadedCount;

    public CursorList(CursorToEntityConverter<E> cursorConverter, Cursor cursor, boolean cacheEntities) {
        this.cursor = cursor;
        this.cursorConverter = cursorConverter;
        size = cursor.getCount();
        if (cacheEntities) {
            entities = new ArrayList<E>(size);
            for (int i = 0; i < size; i++) {
                entities.add(null);
            }
        } else {
            entities = null;
        }
        if (size == 0) {
            cursor.close();
        }

        lock = new ReentrantLock();
    }

    /** Loads the remaining entities (if any) that were not loaded before. Applies to cached lazy lists only. */
    public void loadRemaining() {
        checkCached();
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            get(i);
        }
    }

    protected void checkCached() {
        if (entities == null) {
            throw new RuntimeException("This operation only works with cached lazy lists");
        }
    }

    /** Like get but does not load the entity if it was not loaded before. */
    public E peak(int location) {
        if (entities != null) {
            return entities.get(location);
        } else {
            return null;
        }
    }

    @Override
    /** Closes the underlying cursor: do not try to get entities not loaded (using get) before. */
    public void close() {
        cursor.close();
    }

    public boolean isClosed() {
        return cursor.isClosed();
    }

    public int getLoadedCount() {
        return loadedCount;
    }

    public boolean isLoadedCompletely() {
        return loadedCount == size;
    }

    @Override
    public boolean add(E object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int location, E object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends E> arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object object) {
        loadRemaining();
        return entities.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        loadRemaining();
        return entities.containsAll(collection);
    }

    @Override
    public E get(int location) {
        if (entities != null) {
            E entity = entities.get(location);
            if (entity == null) {
                lock.lock();
                try {
                    entity = entities.get(location);
                    if (entity == null) {
                        entity = loadEntity(location);
                        entities.set(location, entity);
                        // Ignore FindBugs: increment of volatile is fine here because we use a lock
                        loadedCount++;
                        if (loadedCount == size) {
                            cursor.close();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
            return entity;
        } else {
            lock.lock();
            try {
                return loadEntity(location);
            } finally {
                lock.unlock();
            }
        }
    }

    /** Lock must be locked when entering this method. */
    protected E loadEntity(int location) {
        boolean ok = cursor.moveToPosition(location);
        if(!ok) {
            throw new RuntimeException("Could not move to cursor location " + location);
        }
        E entity = cursorConverter.convert(cursor);
        if (entity == null) {
            throw new RuntimeException("Loading of entity failed (null) at position " + location);
        }
        return entity;
    }

    @Override
    public int indexOf(Object object) {
        loadRemaining();
        return entities.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new LazyIterator(0, false);
    }

    @Override
    public int lastIndexOf(Object object) {
        loadRemaining();
        return entities.lastIndexOf(object);
    }

    @Override
    public CloseableListIterator<E> listIterator() {
        return new LazyIterator(0, false);
    }

    /** Closes this list's cursor once the iterator is fully iterated through. */
    public CloseableListIterator<E> listIteratorAutoClose() {
        return new LazyIterator(0, true);
    }

    @Override
    public ListIterator<E> listIterator(int location) {
        return new LazyIterator(location, false);
    }

    @Override
    public E remove(int location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int location, E object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int start, int end) {
        checkCached();
        for (int i = start; i < end; i++) {
            get(i);
        }
        return entities.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        loadRemaining();
        return entities.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        loadRemaining();
        return entities.toArray(array);
    }

}
