package com.dstealer.hellobaby.startup;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Created by LiShiwu on 2/19/2017.
 * Time: 09:51
 * Description:
 */
public class EnvConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);
    private static final Marker QUERY_MARKER = MarkerFactory.getMarker("asd");

    @Test
    public void init() throws Exception {
        EnvConfig.init();
    }

    @Test
    public void logMarkerTest() throws Exception {

        logger.error(QUERY_MARKER, "send to {}", "allskdf adlkf");


    }
}