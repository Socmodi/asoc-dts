package org.asocframework.dts.transaction;

import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public interface DtsBizService {

    DtsActivity start(String bizNo,String bizType,Map<String, Object> attachs);

    void commit(List<DtsAction> actions, DtsBizContext dtsBizContext);

    void rollback(List<DtsAction> actions,DtsBizContext dtsBizContext);

    void finishTx(DtsActivity dtsActivity);

    DtsBizManager getDtsBizManager();

}
