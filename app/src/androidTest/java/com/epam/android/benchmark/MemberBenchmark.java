package com.epam.android.benchmark;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.benchmark.IMember;
import com.squareup.burst.RepeatableSuite;
import com.squareup.burst.annotation.Burst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

/**
 * @author Egor Makovsky
 */
@RunWith(RepeatableSuite.class)
public class MemberBenchmark {
    @Burst Member member;

    private IMember memberImpl;
    private Context context;

    @Before
    public void init() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");

        memberImpl = member.create();
        memberImpl.onActivityCreate(context);
    }

    @After
    public void tearDown() {
        memberImpl.delete(context);
    }

    @Test
    public void testInitAndClear() {

    }

    @Test
    public void testProcess() throws Exception {
        InputStream inputStream = context.getAssets().open("source_10000.json");
        memberImpl.process(context, inputStream);
    }
}
