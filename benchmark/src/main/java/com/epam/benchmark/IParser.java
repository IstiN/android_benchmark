package com.epam.benchmark;

import java.io.InputStream;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public interface IParser {

    interface Listener {
        void onEntityRead(IEntity entity);

        void onReadFinished();
    }

    void init();

    List<IEntity> parse(InputStream inputStream) throws Exception;

    void parse(InputStream inputStream, Listener listener) throws Exception;
}
