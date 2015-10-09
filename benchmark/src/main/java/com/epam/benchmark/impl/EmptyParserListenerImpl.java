package com.epam.benchmark.impl;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;

/**
 * @author Egor Makovsky
 */
public class EmptyParserListenerImpl implements IParser.Listener {
    @Override
    public void onEntityRead(IEntity entity) {

    }

    @Override
    public void onReadFinished() {

    }
}
