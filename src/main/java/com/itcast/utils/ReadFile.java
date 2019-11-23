package com.itcast.utils;

import org.json.JSONObject;

import java.io.*;

/**
 * 文件读取公共类
 * @author 拼命三石
 * @version 2.0
 */
public class ReadFile {

    /**
     * 读取JSON文件
     * @param path 文件路径
     * @return 返回文件内容字符串
     */
    public static String jsonFile(String path) {

        BufferedReader reader = null;

        String message = "";

        try {

            FileInputStream fileInputStream = new FileInputStream(path);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");

            reader = new BufferedReader(inputStreamReader);

            String temp = null;

            while ((temp = reader.readLine()) != null) {

                message += temp;
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (reader != null) {

                try {

                    reader.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return message;
    }
}
