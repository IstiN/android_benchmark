package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.impl.ParserStorageMember;
import com.epam.benchmark.impl.storage.InMemoryStorage;
import com.epam.benchmark.impl.storage.SimpleSQLiteStorage;
import com.epam.benchmark.impl.storage.StubStorage;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;
import com.epam.ormlite.ORMLite;
import com.epam.realmio.RealmIo;

import by.istin.android.xcore.benchmark.XcoreMember;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public class MemberFactory {

    public static IMember getInstance(int memberType) {
        switch (memberType) {
            case 0:
                return new XcoreMember();
            case 1:
                return new ParserStorageMember(new MoshiParser(), new InMemoryStorage());
            case 2:
                return new ParserStorageMember(new JacksonParser(), new InMemoryStorage());
            case 3:
                return new ParserStorageMember(new MoshiParser(), new InMemoryStorage());
            case 4:
                return new ParserStorageMember(new JacksonParser(), new InMemoryStorage());
            case 5:
                return new ParserStorageMember(new JacksonParser(), new SimpleSQLiteStorage());
            case 6:
                return new ParserStorageMember(new JacksonParser(), new RealmIo());
            case 7:
                return new ParserStorageMember(new MoshiParser(), new RealmIo());
            case 8:
                return new ParserStorageMember(new JacksonParser(), new StubStorage());
            case 9:
                return new ParserStorageMember(new MoshiParser(), new StubStorage());
            case 10:
                return new ParserStorageMember(new JacksonParser(), new ORMLite());
            case 11:
                return new ParserStorageMember(new MoshiParser(), new ORMLite());
            default:
                return IMember.Impl.get();
        }
    }

}
