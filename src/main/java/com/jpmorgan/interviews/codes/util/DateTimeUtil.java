package com.jpmorgan.interviews.codes.util;

import java.util.Date;

/**
 * Date util to compute within intervals and times elapsed
 * Created by sadebayo on 28/10/2015.
 */
public class  DateTimeUtil {

    /**
     * Default constructor. Does not require instantiation.
     */
    private DateTimeUtil(){

    }

    /**
     * Method to check whether a trade falls within given interval.
     * @params long timestamp: Trade timestamp
     *         int interval: trade interval (in minutes)
     * @return boolean withinInterval : Indicator for within or not within trade interval.
     */
    public static boolean withinInterval(long created, int interval){
        long now = System.currentTimeMillis();
        double elapsed = (now - created) / (1000.00 * 60);

        return elapsed < interval;
    }

    /**
     * Method to compute time elapsed from trade creation until now
     * @params long created
     * @return double elapsed : Time elapsed between trade creation and now.
     */
    public static double getElapsed(long created){
        long now = System.currentTimeMillis();
        double elapsed = (now - created) / (1000.00 * 60);

        return elapsed;
    }
}
