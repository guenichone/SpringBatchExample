package org.barrak.springbatch.monitoring;

import java.util.HashMap;
import java.util.Map;

/**
 * Instance counter abstract class.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
public abstract class InstanceCounter {

    private static Map<Class, Integer> instanceCounterMap = new HashMap<Class, Integer>();
    private int instanceNumber;

    /* init */
    {
        Integer instanceCounter = instanceCounterMap.get(this.getClass());
        if (instanceCounter == null) {
            instanceCounter = 0;
        }
        instanceCounter++;
        instanceNumber = instanceCounter;
        instanceCounterMap.put(this.getClass(), instanceCounter);
    }

    /**
     * Return current instance number.
     *
     * @return The number of the current object instance.
     */
    public int getInstanceNumber() {
        return instanceNumber;
    }

    @Override
    public String toString() {
        return "id(" + instanceNumber + ")";
    }

}
