package com.lxw.mytool;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {
    /**
     * 为了防止精度损失，禁止使用构造方法 BigDecimal(0.1)的方式把 double 值转
     *化为 BigDecimal 对象,应该用BigDecimal("0.1")
     */
    @Test
    public void test1(){
        BigDecimal a = new BigDecimal(0.1);
        System.out.println(a);//0.1000000000000000055511151231257827021181583404541015625
        BigDecimal b = new BigDecimal("0.1");
        System.out.println(b);//0.1

    }
    @Test
    public void test2(){
        String str = "a,b,c,,";
        String[] ary = str.split(",");
        System.out.println(ary.length);
    }
}
