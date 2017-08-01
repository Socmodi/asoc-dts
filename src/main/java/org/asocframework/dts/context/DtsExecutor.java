package org.asocframework.dts.context;

import java.util.concurrent.*;

/**
 * @author dhj
 * @version $Id: DtsExecutor ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsExecutor {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4,
            8,
            10L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000));

    public static  Future  submit(Callable task){
        if(task==null){
            return null;
        }
        Future future = executor.submit(task);
        return  future;
    }

}
