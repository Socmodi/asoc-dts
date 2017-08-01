package org.asocframework.dts.repair;

import org.asocframework.dts.transaction.DtsBizService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by june on 2017/8/1.
 */

public class DtsRepairLoop  implements RepairLoop{

    private DtsBizService dtsBizService;

    private int period = 300;

    private Timer timer;

    private volatile boolean started = false;

    public DtsRepairLoop(int period) {
        this.period = period;
        timer = new Timer();
    }

    public DtsRepairLoop(int period, DtsBizService dtsBizService) {
        this.period = period;
        this.dtsBizService = dtsBizService;
    }

    @Override
    public void start() {
        timer.schedule(new RepairTask(),3*60*1000,period);
        started = true;
    }

    @Override
    public void shutdown() {
        if(started){
            timer.cancel();
            started = false;
        }
    }

    @Override
    public void repair(String txId) {

    }

    class  RepairTask extends TimerTask {
        @Override
        public void run() {
        }
    }

}
