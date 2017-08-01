package org.asocframework.dts.repair;

/**
 * Created by june on 2017/8/1.
 */
public interface RepairLoop {

    void start();

    void shutdown();

    void repair(String txId);

}
