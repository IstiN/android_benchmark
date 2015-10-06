package com.epam.benchmark.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Egor Makovsky
 */
public interface CloseableList<E> extends List<E>, Closeable {

    class Delegate<E> implements CloseableList<E> {
        private List<E> delegate;
        private Closeable closeable;

        public Delegate(List<E> delegate) {
            this.delegate = delegate;
        }

        public Delegate(List<E> delegate, Closeable closeable) {
            this.delegate = delegate;
            this.closeable = closeable;
        }

        @Override
        public void add(int location, E object) {
            delegate.add(location, object);
        }

        @Override
        public boolean add(E object) {
            return delegate.add(object);
        }

        @Override
        public boolean addAll(int location, Collection<? extends E> collection) {
            return delegate.addAll(location, collection);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return delegate.addAll(collection);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public boolean contains(Object object) {
            return delegate.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return delegate.containsAll(collection);
        }

        @Override
        public E get(int location) {
            return delegate.get(location);
        }

        @Override
        public int indexOf(Object object) {
            return delegate.indexOf(object);
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return delegate.iterator();
        }

        @Override
        public int lastIndexOf(Object object) {
            return delegate.lastIndexOf(object);
        }

        @Override
        public ListIterator<E> listIterator() {
            return delegate.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int location) {
            return delegate.listIterator(location);
        }

        @Override
        public E remove(int location) {
            return delegate.remove(location);
        }

        @Override
        public boolean remove(Object object) {
            return delegate.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return delegate.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return delegate.retainAll(collection);
        }

        @Override
        public E set(int location, E object) {
            return delegate.set(location, object);
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public List<E> subList(int start, int end) {
            return delegate.subList(start, end);
        }

        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @Override
        public <T> T[] toArray(T[] array) {
            return delegate.toArray(array);
        }

        @Override
        public void close() throws IOException {
            if (closeable != null) {
                closeable.close();
            }
        }
    }
}
