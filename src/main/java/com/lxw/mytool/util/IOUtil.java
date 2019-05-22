package com.lxw.mytool.util;


import com.lxw.mytool.config.GlobalConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {


    /**
     * 根据文件全名创建一个utf-8编码的覆盖输出流
     * @param fileName
     * @return
     */
    public static PrintWriter getPrintWriter(String fileName) {
        PrintWriter pw = getPrintWriter(fileName, "utf-8");
        return pw;
    }

    /**
     * 根据文件全名创建一个指定编码的覆盖输出流
     * @param fileName
     * @param csn
     * @return
     */
    public static PrintWriter getPrintWriter(String fileName,String csn) {
        PrintWriter pw = null;
        try {
//        文件不存在时就自动创建文件
            pw = new PrintWriter(fileName,csn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * 获取一个可以追加的输出PrintWriter
     * @param fileName
     * @param append
     * @return
     */
    public static PrintWriter getPrintWriter(String fileName,boolean append) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName,append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        return pw;
    }


    /**
     * 如果目录不存在就创建多级目录
     * 如果文件不存在就创建文件
     * 如果文件存在就删除文件然后创建文件
     * @param fileName
     * @return
     */
    public static File getFile(String fileName) {
        File file = new File(fileName);
//        如果文件路径不存在就创建路径
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
//        如果文件不存在就创建文件,如果文件存在就删除重新创建新文件
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 将字符串输出到指定的位置
     * 注意:只要流关闭再此调用该方法输出到原来的文件会覆盖掉之前的内容,
     * 因此在循环里面调用只得到最后一个,在循环里面调用可以使用本类的getPrintWriter方法
     *
     * @param fileName
     * @param str
     */
    public static void print2File(String fileName, String str) {
        PrintWriter pw = getPrintWriter(fileName);
        if(str!=null){
            pw.print(str);
            pw.flush();
        }
        pw.close();
        System.out.println("文件:" + fileName + "输出完毕");
    }

    public static void print2RunTxt( String str) {
        String fileName = GlobalConfig.loggerPath+"consele.txt";
        print2File(fileName,str);
        String historyFileName = GlobalConfig.loggerPath+"history\\"+"consele"+System.currentTimeMillis()+".txt";
        print2File(historyFileName,str);
    }

    /**
     * 扫描指定路径下的文件
     *
     * @param file
     * @param files
     */
    public static List<File> getFiles(File file, List<File> files) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isFile()) {
                files.add(f);
            } else {
                getFiles(f, files);
            }
        }
        return files;
    }

    /**
     * 逐行读取
     *
     * @param fin
     * @throws IOException
     */
    public static List<String> readFileByLine(File fin) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fin));
        String line = null;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        return list;
    }

}
