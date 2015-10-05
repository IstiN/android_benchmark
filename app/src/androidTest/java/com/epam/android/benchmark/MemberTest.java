package com.epam.android.benchmark;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.epam.benchmark.IMember;
import com.squareup.burst.BurstJUnit4;
import com.squareup.burst.annotation.Burst;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

/**
 * @author Egor Makovsky
 */
@RunWith(BurstJUnit4.class)
public class MemberTest {
    @Rule public final ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Burst Member member;

    private IMember memberImpl;

    @Before
    public void init() {
        memberImpl = member.create();
        memberImpl.onActivityCreate(activityRule.getActivity());
        Log.d("test", "init: " + memberImpl);
    }

    @Test
    public void testProcess() throws Exception {
        InputStream inputStream = activityRule.getActivity().getAssets().open("source_10000.json");
        memberImpl.process(activityRule.getActivity(), inputStream);

        Log.d("test", "n: " + memberImpl.getCachedEntities(activityRule.getActivity()).get(0));
    }
}
