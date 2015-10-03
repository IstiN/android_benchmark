package com.epam.greendao;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by shulha_dmytro on 2.10.15.
 */
public class Generator {
    public static void main(String[] args) {
        try {
            Schema schema = new Schema(1, "com.epam.greendao");
            schema.enableKeepSectionsByDefault();
            Entity entity = schema.addEntity("Model");


            entity.addStringProperty("id").primaryKey();
            entity.addIntProperty("index");
            entity.addBooleanProperty("isActive");
            entity.addStringProperty("picture");
            entity.addStringProperty("name");
            entity.addStringProperty("company");
            entity.addStringProperty("email");
            entity.addStringProperty("about");
            entity.addStringProperty("registered");
            entity.addDoubleProperty("latitude");
            entity.addDoubleProperty("longitude");

            new DaoGenerator().generateAll(schema, "greendao/src/main/java/");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
