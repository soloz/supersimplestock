package com.jpmorgan.interviews.codes.api.model;

/**
 * Created by sadebayo on 27/10/2015.
 */
public interface Entity {

    public String getName();

    public String getEntityType();

    public void setTimeStamp(long timeStamp);

    public long getTimeStamp();

    public double getValue();
}
