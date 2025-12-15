package org.anthony.library.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryLogger {
    private static final Logger logger = LoggerFactory.getLogger(LibraryLogger.class);

    public static Logger getLogger() { return logger; }
    public static void LogException(Exception e) 
    {
        logger.error("An error occured", e);
    }

    public static void LogAssertEq(Object l, Object r, String msg) {
        if (!l.equals(r)) logger.error("Assertion Failed: ", msg);
    }
}
