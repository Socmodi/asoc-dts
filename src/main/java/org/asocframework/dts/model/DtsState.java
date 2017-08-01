package org.asocframework.dts.model;

/**
 * @author dhj
 * @version $Id: DtsState ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public enum DtsState {

    INIT(0,"INIT"),
    COMMIT(1,"COMMIT"),
    ROLLBACK(2,"ROLLBACK"),
    UNKNOWN(3,"UNKNOWN");

    private int value;
    private String name;

    DtsState(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static DtsState valueOf(int value) {
        for(DtsState rcode : DtsState.values()){
            if(rcode.value == value)
                return rcode;
        }
        return null;
    }

    public static DtsState get(String name) {
        for(DtsState config : DtsState.values()){
            if(config.name.equals(name))
                return config;
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
