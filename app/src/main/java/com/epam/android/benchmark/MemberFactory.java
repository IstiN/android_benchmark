package com.epam.android.benchmark;

import com.epam.benchmark.IMember;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public class MemberFactory {

    public static IMember getInstance(int memberType) {
        switch (memberType) {

            default:
                return IMember.Impl.get();
        }
    }

}
