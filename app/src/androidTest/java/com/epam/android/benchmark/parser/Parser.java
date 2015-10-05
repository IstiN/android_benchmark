package com.epam.android.benchmark.parser;

import com.epam.benchmark.IParser;
import com.epam.benchmark.jackson.JacksonParser;
import com.epam.benchmark.moshi.MoshiParser;

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
    };

    public abstract IParser create();
}
