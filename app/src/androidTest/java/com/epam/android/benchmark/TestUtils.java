package com.epam.android.benchmark;

import android.content.Context;
import android.os.Environment;

import com.epam.benchmark.IEntity;
import com.epam.greendao.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Egor Makovsky
 */
public class TestUtils {
    private static final String LONG_NAME = "afdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwefafdaswqewfewqfwqfasfqwef";

    private static final String JSON_FOLDER = "benchmark";

    public static InputStream getJsonInputStream(Context context, int count) throws IOException {
        String fileName = String.format("source_%s.json", count);
        return count <= 1000 ? getJsonInputStreamFromAssets(context, fileName) : getJsonInputStreamFromFile(fileName);
    }

    public static InputStream getJsonInputStreamFromAssets(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    public static InputStream getJsonInputStreamFromFile(String fileName) throws FileNotFoundException {
        return new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + JSON_FOLDER + File.separator + fileName);
    }

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

    public static String formatTime(long time) {
        return String.format(Locale.ENGLISH, "%.3f", (System.currentTimeMillis() - time) / 1000.);
    }
}
