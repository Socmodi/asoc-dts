package org.asocframework.dts;

/**
 * @author dhj
 * @version $Id: DbType ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public enum DbType {

    MYSQL(0,"MYSQL"),
    MONGO(1,"MONGO"),
    ORACLE(2,"ORACLE");


    private int value;
    private String name;

    DbType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static DbType valueOf(int value) {
        for(DbType rcode : DbType.values()){
            if(rcode.value == value)
                return rcode;
        }
        return null;
    }

    public static DbType get(String name) {
        for(DbType config : DbType.values()){
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
