package com.epam.benchmark.util;

import java.io.Closeable;
import java.util.ListIterator;

/**
 * @author Egor Makovsky
 */
public interface CloseableListIterator<T> extends ListIterator<T>, Closeable {

}