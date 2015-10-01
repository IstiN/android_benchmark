package com.epam.android.benchmark;

import android.app.Application;
import android.content.Context;

import com.epam.benchmark.IMember;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public class BenchmarkApplication extends Application {

    public static final String MEMBER_SYSTEM_KEY = "member_key";

    public static final String MEMBER_CREATION_TIME = "member_creation_time";

    private long mMemberApplicationOnCreateTime = 0;

    private IMember mMember;

    @Override
    public void onCreate() {
        super.onCreate();
        createMember();
    }

    private void createMember() {
        long now = System.currentTimeMillis();
        mMember = MemberFactory.getInstance(5);
        mMember.onApplicationCreate(this);
        mMemberApplicationOnCreateTime = System.currentTimeMillis() - now;
    }

    @Override
    public Object getSystemService(String name) {
        if (MEMBER_SYSTEM_KEY.equals(name)) {
            if (mMember != null) {
                createMember();
            }
            return mMember;
        } else if (MEMBER_CREATION_TIME.equals(name)) {
            return mMemberApplicationOnCreateTime;
        }
        Object systemService = mMember.getSystemService(name);
        if (systemService != null) {
            return systemService;
        }
        return super.getSystemService(name);
    }

    public static <T> T get(Context context, String name) {
        if (context == null || name == null) {
            throw new IllegalArgumentException("Context and key must not be null");
        }
        T systemService = (T) context.getSystemService(name);
        if (systemService == null) {
            context = context.getApplicationContext();
            systemService = (T) context.getSystemService(name);
        }
        if (systemService == null) {
            throw new IllegalStateException(name + " not available");
        }
        return systemService;
    }

}
