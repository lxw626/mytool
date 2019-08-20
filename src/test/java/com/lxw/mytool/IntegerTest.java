package com.lxw.mytool;

import org.junit.Test;

import java.math.BigDecimal;

public class IntegerTest {
    @Test
    public void test1(){
        Integer a = 1;
        Integer b = 1;
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }
    @Test
    public void test2(){
        Integer a = 1000;
        Integer b = 1000;
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }
    @Test
    public void test3(){
//        float a = 1.0f-0.9f;
//        float b = 0.9f-0.8f;
        float a = 0.1f;
        float b = 0.1f;
        System.out.println(a);
        System.out.println(b);
        Float a2 = Float.valueOf(a);
        Float b2 = Float.valueOf(b);
        System.out.println(a==b);
        System.out.println(a2.equals(b2));
        double sqrt = Math.sqrt(-1);
        double v = 0.0 / 0.0;
        System.out.println(sqrt);
        System.out.println(v);
        BigDecimal g = new BigDecimal(0.1);
        System.out.println(g);//0.1000000000000000055511151231257827021181583404541015625
    }


}
