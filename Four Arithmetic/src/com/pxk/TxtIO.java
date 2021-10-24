package com.pxk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pxk
 * @date 2021年10月23日 19:43
 *
 * 读写txt文件的工具类
 */
public class TxtIO {

    /**
     * 删除文件里的内容
     *
     * @param txtPath 文件路径
     */
    public static void clear(String txtPath) {
        File file = new File(txtPath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入txt文件
     * 传入内容、文件全路径名，将内容写入文件并换行
     *
     * @param txtTitle 传入的内容
     * @param txtPath  写入的文件路径
     */
    public static void writeTxt(String txtTitle, String txtPath) {
        File file = new File(txtPath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write(txtTitle, 0, txtTitle.length());
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取txt文件
     * 传入文件绝对路径，将文件内容转化为 String字符串输出
     *
     * @param txtPath 文件路径
     * @return 文件内容
     */
    public static List<String> readTxt(String txtPath) {
        File file = new File(txtPath);
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}
