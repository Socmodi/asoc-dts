package org.asocframework.dts.model;

import java.lang.annotation.*;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DtsBizAction {

    String name() default "";

    String commitMethod() default "";

    String rollbackMethod() default "";

    boolean nesting() default false;

}
