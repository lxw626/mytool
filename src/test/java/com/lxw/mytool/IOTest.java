package com.lxw.mytool;

import com.lxw.mytool.config.GlobalConfig;
import com.lxw.mytool.util.IOUtil;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IOTest {
    @Test
    public void test1() throws Exception{
        String fileName = GlobalConfig.loggerPath+"console2.txt";
//        文件不存在时就自动创建文件
        PrintWriter printWriter = new PrintWriter(fileName,"utf-8");
        printWriter.print("hello,中国!");
        printWriter.print("hello,java!");
        printWriter.print("hello,idea!");
        printWriter.flush();
        printWriter.close();
    }
    @Test
    public void t2() throws IOException {
        String fileName = GlobalConfig.loggerPath+"console3.txt";
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("hello,中国!");
        pw.flush();
        pw.close();
    }
    @Test
    public void testt3(){
        IOUtil.print2RunTxt("TEST");
    }
}
