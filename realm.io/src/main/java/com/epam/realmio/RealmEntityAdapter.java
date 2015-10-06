package com.epam.realmio;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.util.CloseableList;

import java.io.IOException;
import java.util.AbstractList;

import io.realm.RealmResults;

/**
 * @author Egor Makovsky
 */
public class RealmEntityAdapter extends AbstractList<IEntity> implements CloseableList<IEntity> {
    private RealmResults<Model> items;

    public RealmEntityAdapter(RealmResults<Model> items) {
        this.items = items;
    }

    @Override
    public IEntity get(int location) {
        return new ModelWrapper(items.get(location));
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void close() throws IOException {

    }
}
