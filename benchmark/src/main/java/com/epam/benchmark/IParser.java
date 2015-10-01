package com.epam.benchmark;

import java.io.InputStream;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public interface IParser {

    void init();

    List<IEntity> parse(InputStream inputStream) throws Exception;
}
