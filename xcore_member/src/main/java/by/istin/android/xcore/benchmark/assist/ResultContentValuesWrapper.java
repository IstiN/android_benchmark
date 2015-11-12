package by.istin.android.xcore.benchmark.assist;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.util.CloseableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.utils.Log;
import by.istin.android.xcore.utils.StringUtil;
import by.istin.android.xcore.utils.XRecycler;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class ResultContentValuesWrapper implements CloseableList<IEntity> {

    public static class ContentValuesEntity implements IEntity {

        public ContentValues mContentValues;

        @Override
        public String getId() {
            return mContentValues.getAsString(SourceEntity.ID_AS_STRING);
        }

        @Override
        public Integer getIndex() {
            return mContentValues.getAsInteger(SourceEntity.INDEX);
        }

        @Override
        public Boolean isActive() {
            return mContentValues.getAsBoolean(SourceEntity.IS_ACTIVE);
        }

        @Override
        public String getPicture() {
            return mContentValues.getAsString(SourceEntity.PICTURE);
        }

        @Override
        public String getEmployeeName() {
            return mContentValues.getAsString(SourceEntity.NAME);
        }

        @Override
        public String getEmployeeCompany() {
            return mContentValues.getAsString(SourceEntity.COMPANY);
        }

        @Override
        public String getEmployeeEmail() {
            return mContentValues.getAsString(SourceEntity.EMAIL);
        }

        @Override
        public String getEmployeeAbout() {
            return mContentValues.getAsString(SourceEntity.ABOUT);
        }

        @Override
        public String getEmployeeRegisteredFormatted() {
            return mContentValues.getAsString(SourceEntity.REGISTERED);
        }

        @Override
        public Double getLatitude() {
            return mContentValues.getAsDouble(SourceEntity.LATITUDE);
        }

        @Override
        public Double getLongitude() {
            return mContentValues.getAsDouble(SourceEntity.LONGITUDE);
        }

        @Override
        public String getTags() {
            return mContentValues.getAsString(SourceEntity.TAGS);
        }

        @Override
        public void print() {
            Log.xd(this, StringUtil.joinAll(
                    "|",
                    getId(),
                    getIndex(),
                    isActive(),
                    getPicture(),
                    getEmployeeName(),
                    getEmployeeCompany(),
                    getEmployeeEmail(),
                    getEmployeeAbout(),
                    getEmployeeRegisteredFormatted(),
                    getLatitude(),
                    getLongitude(),
                    getTags()));
        }
    }

    private XRecycler<ContentValuesEntity> mRecycler = new XRecycler<>(new XRecycler.RecyclerElementCreator<ContentValuesEntity>() {
        @Override
        public ContentValuesEntity createNew(XRecycler pRecycler) {
            return new ContentValuesEntity();
        }
    });

    private ContentValuesEntity mValuesEntity;

    private List<ContentValues> mContentValuesList;

    public ResultContentValuesWrapper(List<ContentValues> pContentValuesList) {
        mContentValuesList = pContentValuesList;
    }

    @Override
    public void close() {
        mContentValuesList = null;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEntity get(int location) {
        final ContentValues contentValues = mContentValuesList.get(location);
        return getValuesEntity(contentValues);
    }

    @NonNull
    private ContentValuesEntity getValuesEntity(ContentValues pContentValues) {
        if (mValuesEntity != null) {
            mValuesEntity.mContentValues = null;
            mRecycler.recycled(mValuesEntity);
        }
        final ContentValuesEntity contentValuesEntity = mRecycler.get();
        contentValuesEntity.mContentValues = pContentValues;
        return contentValuesEntity;
    }

    @Override
    public int indexOf(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return mContentValuesList.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<IEntity> iterator() {
        final Iterator<ContentValues> iterator = mContentValuesList.iterator();
        return new Iterator<IEntity>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public IEntity next() {
                return getValuesEntity(iterator.next());
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
        final ListIterator<ContentValues> cursorListIterator = mContentValuesList.listIterator();
        return createIterator(cursorListIterator);
    }

    private ListIterator<IEntity> createIterator(final ListIterator<ContentValues> cursorListIterator) {
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
                return getValuesEntity(cursorListIterator.next());
            }

            @Override
            public int nextIndex() {
                return cursorListIterator.nextIndex();
            }

            @Override
            public IEntity previous() {
                return getValuesEntity(cursorListIterator.previous());
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
        if (mContentValuesList == null) {
            return 0;
        }
        return mContentValuesList.size();
    }

    @NonNull
    @Override
    public ListIterator<IEntity> listIterator(int location) {
        final ListIterator<ContentValues> cursorListIterator = mContentValuesList.listIterator(location);
        return createIterator(cursorListIterator);
    }

    @Override
    public int lastIndexOf(Object object) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        throw new UnsupportedOperationException();
    }
}
