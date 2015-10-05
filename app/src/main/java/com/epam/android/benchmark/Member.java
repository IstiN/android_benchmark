package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.impl.ParserStorageMember;
import com.epam.benchmark.impl.storage.InMemoryStorage;
import com.epam.benchmark.impl.storage.SQLiteStorage;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;
import com.epam.greendao.GreenDAO;
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

    MOSHI_GREEN_DAO {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new GreenDAO());
        }
    },

    JACKSON_IN_MEMORY {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new InMemoryStorage());
        }
    },

    JACKSON_SQLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new SQLiteStorage());
        }
    },

    JACKSON_REALM {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new RealmIo());
        }
    },

    JACKSON_GREENDAO {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new GreenDAO());
        }
    },


;
//    case 5:
//        return new ParserStorageMember(new JacksonParser(), new SimpleSQLiteStorage());
//    case 6:
//        return new ParserStorageMember(new JacksonParser(), new RealmIo());
//    case 7:
//        return new ParserStorageMember(new MoshiParser(), new RealmIo());
//    case 8:
//        return new ParserStorageMember(new JacksonParser(), new StubStorage());
//    case 9:
//        return new ParserStorageMember(new MoshiParser(), new StubStorage());
//    case 10:
//        return new ParserStorageMember(new JacksonParser(), new ORMLite());
//    case 11:
//        return new ParserStorageMember(new MoshiParser(), new ORMLite());
//    case 12:
//        return new ParserStorageMember(new MoshiParser(), new GreenDAO());
//    case 13:
//        return new ParserStorageMember(new JacksonParser(), new GreenDAO());



    public abstract IMember create();
}
