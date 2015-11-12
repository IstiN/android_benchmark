package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.impl.ParserStorageMember;
import com.epam.benchmark.impl.storage.SQLiteStorage;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;
import com.epam.greendao.GreenDAO;
import com.epam.ormlite.ORMLite;
import com.epam.realmio.RealmIo;

import by.istin.android.xcore.benchmark.XcoreMember;
import by.istin.android.xcore.benchmark.XcoreParser;
import by.istin.android.xcore.benchmark.XcoreStorage;

/**
 * @author Egor Makovsky
 */
public enum Member {

    //===========JACKSON
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

    JACKSON_ORMLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new ORMLite());
        }
    },
    JACKSON_XCORE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new JacksonParser(), new XcoreStorage());
        }
    },

    //===========MOSHI

    MOSHI_SQLITE {
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
    MOSHI_XCORE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new MoshiParser(), new XcoreStorage());
        }
    },

    //===========XCORE

    XCORE_SQLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new XcoreParser(), new SQLiteStorage());
        }
    },

    XCORE_REALM {
        @Override
        public IMember create() {
            return new ParserStorageMember(new XcoreParser(), new RealmIo());
        }
    },

    XCORE_GREENDAO {
        @Override
        public IMember create() {
            return new ParserStorageMember(new XcoreParser(), new GreenDAO());
        }
    },

    XCORE_ORMLITE {
        @Override
        public IMember create() {
            return new ParserStorageMember(new XcoreParser(), new ORMLite());
        }
    },
    X_CORE {
        @Override
        public IMember create() {
            return new XcoreMember();
        }
    }
    ;

    public abstract IMember create();
}
