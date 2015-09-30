package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.jackson.JacksonMember;
import com.epam.benchmark.moshi.MoshiMember;

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
                return new MoshiMember();
            case 2:
                return new JacksonMember();

            default:
                return IMember.Impl.get();
        }
    }

}
