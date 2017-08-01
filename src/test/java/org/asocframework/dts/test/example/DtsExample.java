package org.asocframework.dts.test.example;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.asocframework.dts.test.biz.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author dhj
 * @version $Id: DtsExample ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DtsExample {

    @Resource
    private TransferService transferService;

    @Test
    public void  simple() throws InterruptedException {

        Map<String,Boolean> map = new HashMap<>();
        Boolean b = map.get("test");
//        try {
//            Method method =  transferService.getClass().getMethod("tranfer",null);
//            System.out.println(method.getDeclaringClass().getName());
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        transferService.tranfer();

        Thread.sleep(10000L);
    }

}
