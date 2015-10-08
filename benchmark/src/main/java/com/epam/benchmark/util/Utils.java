package com.epam.benchmark.util;

import android.text.TextUtils;
import android.util.Log;

import com.epam.benchmark.IEntity;

/**
 * Created by shulha_dmytro on 2.10.15.
 */
public class Utils {
    public static void print(String database, IEntity model) {
        Log.d(database, TextUtils.join(
            "|", new Object[]{
                model.getId(),
                model.getIndex(),
                model.isActive(),
                model.getPicture(),
                model.getEmployeeName(),
                model.getEmployeeCompany(),
                model.getEmployeeEmail(),
                model.getEmployeeAbout(),
                model.getEmployeeRegisteredFormatted(),
                model.getLatitude(),
                model.getLongitude(),
                model.getTags()}));
    }

    public static void logMemory() {
        // Get current size of heap in bytes
        long heapSize = Runtime.getRuntime().totalMemory();

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
        // Any attempt will result in an OutOfMemoryException.
        long heapMaxSize = Runtime.getRuntime().maxMemory();

        // Get amount of free memory within the heap in bytes. This size will increase
        // after garbage collection and decrease as new objects are created.
        long heapFreeSize = Runtime.getRuntime().freeMemory();

        Log.d("test", String.format("MEMORY: %s, %s, %s", heapSize / 1024 / 1024, heapMaxSize / 1024 / 1024, heapFreeSize / 1024 / 1024));
    }
}
