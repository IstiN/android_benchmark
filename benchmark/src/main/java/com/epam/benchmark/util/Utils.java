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
}
