package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.impl.InMemoryStorage;
import com.epam.benchmark.impl.ParserStorageMember;
import com.epam.benchmark.impl.SimpleSQLiteStorage;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;

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
                return new ParserStorageMember(new JacksonParser(), new SimpleSQLiteStorage());

            default:
                return IMember.Impl.get();
        }
    }

}
