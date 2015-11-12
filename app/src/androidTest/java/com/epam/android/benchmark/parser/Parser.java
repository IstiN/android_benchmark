package com.epam.android.benchmark.parser;

import com.epam.benchmark.IParser;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;

import by.istin.android.xcore.benchmark.XcoreParser;

/**
 * @author Egor Makovsky
 */
public enum Parser {
    MOSHI {
        @Override
        public IParser create() {
            return new MoshiParser();
        }
    },
    JACKSON {
        @Override
        public IParser create() {
            return new JacksonParser();
        }
    },
    XCORE {
        @Override
        public IParser create() {
            return new XcoreParser();
        }
    };

    public abstract IParser create();
}
