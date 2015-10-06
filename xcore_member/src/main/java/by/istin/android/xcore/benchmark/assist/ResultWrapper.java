package by.istin.android.xcore.benchmark.assist;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.epam.benchmark.util.CloseableList;
import com.epam.benchmark.IEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import by.istin.android.xcore.utils.CursorUtils;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class ResultWrapper implements CloseableList<IEntity> {

    private EntityCursorModel mCursorModel;

    public ResultWrapper(EntityCursorModel cursorModel) {
        mCursorModel = cursorModel;
    }

    @Override
    public void close() {
        CursorUtils.close(mCursorModel);
        mCursorModel = null;
    }

    @Override
    public void add(int location, IEntity object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(IEntity object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int location, Collection<? extends IEntity> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends IEntity> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object object) {
        return mCursorModel.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mCursorModel.containsAll(collection);
    }

    @Override
    public IEntity get(int location) {
        return (IEntity) mCursorModel.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return mCursorModel.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return mCursorModel.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<IEntity> iterator() {
        final Iterator<Cursor> iterator = mCursorModel.iterator();
        return new Iterator<IEntity>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public IEntity next() {
                return (IEntity) iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    @NonNull
    @Override
    public ListIterator<IEntity> listIterator() {
        final ListIterator<Cursor> cursorListIterator = mCursorModel.listIterator();
        return createIterator(cursorListIterator);
    }

    private ListIterator<IEntity> createIterator(final ListIterator<Cursor> cursorListIterator) {
        return new ListIterator<IEntity>() {
            @Override
            public void add(IEntity object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return cursorListIterator.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return cursorListIterator.hasPrevious();
            }

            @Override
            public IEntity next() {
                return (IEntity)cursorListIterator.next();
            }

            @Override
            public int nextIndex() {
                return cursorListIterator.nextIndex();
            }

            @Override
            public IEntity previous() {
                return (IEntity)cursorListIterator.previous();
            }

            @Override
            public int previousIndex() {
                return cursorListIterator.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(IEntity object) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public int size() {
        if (mCursorModel == null) {
            return 0;
        }
        return mCursorModel.size();
    }

    @NonNull
    @Override
    public ListIterator<IEntity> listIterator(int location) {
        final ListIterator<Cursor> cursorListIterator = mCursorModel.listIterator(location);
        return createIterator(cursorListIterator);
    }

    @Override
    public int lastIndexOf(Object object) {
        return mCursorModel.lastIndexOf(object);
    }

    @Override
    public IEntity remove(int location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return mCursorModel.retainAll(collection);
    }

    @Override
    public IEntity set(int location, IEntity object) {
        throw new UnsupportedOperationException();
    }


    @NonNull
    @Override
    public List<IEntity> subList(int start, int end) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mCursorModel.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return mCursorModel.toArray(array);
    }
}
