package com.epam.android.benchmark.storage;

import com.epam.benchmark.IEntity;
import com.epam.greendao.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class StorageTestUtils {
    private static final String LONG_NAME = "afdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwef";

    public static List<IEntity> newTestList(int size) {
        List<IEntity> entities = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            entities.add(newEntity("id" + i, true, LONG_NAME, i));
        }
        return entities;
    }

    public static List<IEntity> newTestList() {
        List<IEntity> entities = new ArrayList<>();
        entities.add(newEntity("id1", true, "name1", 1));
        entities.add(newEntity("id2", true, "name2", 2));
        entities.add(newEntity("id3", false, "name3", 3));
        entities.add(newEntity("id4", true, "name4", 10));
        entities.add(newEntity("id5", true, "name5", 15));
        return entities;
    }

    public static IEntity newEntity(String id, Boolean isActive, String name, Integer index) {
        Model model = new Model();
        model.setId(id);
        model.setIsActive(isActive);
        model.setName(name);
        model.setIndex(index);
        model.setLatitude(0d);
        model.setLongitude(0d);
        return model;
    }
}
