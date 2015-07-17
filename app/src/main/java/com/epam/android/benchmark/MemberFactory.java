package com.epam.android.benchmark;

import com.epam.benchmark.IMember;

import by.istin.android.xcore.benchmark.XcoreMember;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public class MemberFactory {

    public static IMember getInstance(int memberType) {
        switch (memberType) {
            case 0:
                return new XcoreMember();
            default:
                return IMember.Impl.get();
        }
    }

}
