package org.barrak.springbatch.monitoring;

/**
 * Instance counter abstract class.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
public abstract class InstanceCounter {

    private static int instanceCounter = 0;
    private int instanceNumber;

    /* init */
    {
        instanceCounter++;
        instanceNumber = instanceCounter;
    }

    public int getInstanceNumber() {
        return instanceNumber;
    }

    @Override
    public String toString() {
        return "id(" + instanceNumber + ")";
    }

}
