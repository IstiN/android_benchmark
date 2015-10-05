package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.impl.ParserStorageMember;
import com.epam.benchmark.impl.storage.InMemoryStorage;
import com.epam.benchmark.impl.storage.SQLiteStorage;
import com.epam.benchmark.moshi.MoshiParser;
import com.epam.greendao.GreenDAO;
import com.epam.ormlite.ORMLite;
import com.epam.realmio.RealmIo;

import by.istin.android.xcore.benchmark.XcoreMember;

/**
 * @author Egor Makovsky
 */
public enum Member {
    X_CORE {
        @Override
        public IMember create() {
            return new XcoreMember();
        }
    },

    MOSHI_IN_MEMORY {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new InMemoryStorage());
        }
    },

    MOSHI_SIMPLE_SQLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new SQLiteStorage());
        }
    },

    MOSHI_REALM {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new RealmIo());
        }
    },

    MOSHI_GREENDAO {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new GreenDAO());
        }
    },

    MOSHI_ORMLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new ORMLite());
        }
    },
//
//    JACKSON_IN_MEMORY {
//        @Override
//        public IMember create() {
//            return new ParserStorageMember(new JacksonParser(), new InMemoryStorage());
//        }
//    },
//
//    JACKSON_SQLITE {
//        @Override
//        public IMember create() {
//            return new ParserStorageMember(new JacksonParser(), new SQLiteStorage());
//        }
//    },
//
//    JACKSON_REALM {
//        @Override
//        public IMember create() {
//            return new ParserStorageMember(new JacksonParser(), new RealmIo());
//        }
//    },
//
//    JACKSON_GREENDAO {
//        @Override
//        public IMember create() {
//            return new ParserStorageMember(new JacksonParser(), new GreenDAO());
//        }
//    },
//
//    JACKSON_REALM {
//        @Override
//        public IMember create() {
//            return new ParserStorageMember(new JacksonParser(), new RealmIo());
//        }
//    }
    ;


    public abstract IMember create();
}
