package org.asocframework.dts;

/**
 * @author dhj
 * @version $Id: DtsException ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsException extends RuntimeException{

    public DtsException(String s) {
        super(s);
    }

    public DtsException(String s, Exception e) {
        super(s,e);
    }
}
