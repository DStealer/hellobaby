package com.dstealer.hellobaby.unknown;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.StringReader;
import java.util.Properties;

/**
 * Created by LiShiwu on 02/20/2017.
 */
public class Log4j2Test {
    private static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);

    @Test
    public void tt1() throws Exception {
        LOGGER.info("您好，测试啊");
        LOGGER.info(new Object() {
            @Override
            public String toString() {
                return "$classname{}";
            }
        });
    }

    @Test
    public void tt2() throws Exception {
        Properties properties = new Properties();
        properties.load(new StringReader("a:b\nb:c\nc:d"));
        System.out.println(properties.size());
        System.out.println(properties);
    }

    @Test
    public void tt3() throws Exception {
        System.out.println(System.getProperty("user.dir"));
    }
}
