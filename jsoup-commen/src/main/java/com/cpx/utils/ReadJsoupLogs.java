package com.cpx.utils;

import net.sf.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadJsoupLogs {

    private static int i = 1;
    static JSONObject json = null;
    public static synchronized JSONObject result(){

        try{
            String resultVal  = readFileFromEnd("E:\\home\\tools\\logs\\appFatel.log","GBK",i);
            if(resultVal==null){
                return json;
            }
            System.out.println("=============="+resultVal);
            String result =  resultVal.split("position=")[1];
            System.out.println(result);
            JSONObject jsonObject = JSONObject.fromObject(result);
        }catch (Exception e){
            System.out.println("9999999999999");
            i++;
            return result();
        }finally {
            return json;
        }
    }

    /**
     * 从文件末尾开始读取文件，并逐行打印
     * @param filename  file path
     * @param charset character
     */
    public static String readFileFromEnd(String filename, String charset,int readNum) {

        String resultStr = "";

        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filename, "r");
            long fileLength = rf.length();
            long start = rf.getFilePointer();// 返回此文件中的当前偏移量
            long readIndex = start + fileLength -1;
            if(readIndex<=0){
                return null;
            }
            String line;
            rf.seek(readIndex);// 设置偏移量为文件末尾
            int c = -1;
            int breakNum = 1;

            while (readIndex > start) {
                c = rf.read();
                String readText = null;
                if (c == '\n' || c == '\r') {
                    line = rf.readLine();
                    if (line != null) {
                        readText = new String(line.getBytes("ISO-8859-1"), charset);
                    } else {
                        System.out.println("read line : " + line);
                    }
                    readIndex--;
                }
                readIndex--;
                rf.seek(readIndex);
                if (readIndex == 0) {// 当文件指针退至文件开始处，输出第一行
                    resultStr = readText = rf.readLine();
                    if(breakNum==readNum){
                        break;
                    }
                    breakNum++;
                }
                if (readText != null) {
                    resultStr = readText;
                    if(breakNum==readNum){
                        break;
                    }
                    breakNum++;
                }

            }
            return resultStr;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rf != null){
                    rf.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

