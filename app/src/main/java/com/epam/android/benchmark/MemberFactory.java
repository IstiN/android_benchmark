package com.epam.android.benchmark;

import com.epam.benchmark.IMember;
import com.epam.benchmark.JacksonMember;
import com.epam.benchmark.MoshiMember;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public class MemberFactory {

    public static IMember getInstance(int memberType) {
        switch (memberType) {
            case 1:
                return new MoshiMember();
            case 2:
                return new JacksonMember();

            default:
                return IMember.Impl.get();
        }
    }

}
