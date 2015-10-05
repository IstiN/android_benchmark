package com.epam.android.benchmark.storage;

import com.epam.benchmark.IStorage;
import com.epam.benchmark.impl.storage.InMemoryStorage;
import com.epam.benchmark.impl.storage.SimpleSQLiteStorage;
import com.epam.greendao.GreenDAO;
import com.epam.ormlite.ORMLite;
import com.epam.realmio.RealmIo;

/**
 * @author Egor Makovsky
 */
public enum Storage {
    IN_MEMORY {
        @Override
        public IStorage create() {
            return new InMemoryStorage();
        }
    },

    SIMPLE {
        @Override
        public IStorage create() {
            return new SimpleSQLiteStorage();
        }
    },

    ORMLITE {
        @Override
        public IStorage create() {
            return new ORMLite();
        }
    },

    REALM {
        @Override
        public IStorage create() {
            return new RealmIo();
        }
    },

    GREEN_DAO {
        @Override
        public IStorage create() {
            return new GreenDAO();
        }
    };

    public abstract IStorage create();
}
