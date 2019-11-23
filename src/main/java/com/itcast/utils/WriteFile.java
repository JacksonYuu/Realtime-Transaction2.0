package com.itcast.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件写入工具类
 * @author 拼命三石
 * @version 2.0
 */
public class WriteFile {

    private static String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/hadoop.json");

    private static JSONObject jsonObject = new JSONObject(jsonContext);

    private static FileSystem fs = null;

    private static String hdfs = jsonObject.getString("hdfs");

    private static String path = jsonObject.getString("path");

    private static String type = jsonObject.getString("type");

    private static String local = jsonObject.getString("local");

    private static String topic = jsonObject.getString("topic");

    /**
     * 将信息写入本地文件系统当中
     * @param around 发送地
     * @return 返回FileWriter对象
     */
    public static FileWriter writeLocal(String around) {

        String s = path + "/" + topic + "/" + around + type;

        String file = local + s;

        File f = new File(file);

        if (!f.exists()) {

            //根据文件路径创建文件目录
            mkdir(file);
        }

        try {

            return new FileWriter(f);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据文件路径创建文件目录
     * @param file 文件路径
     */
    public static void mkdir(String file) {

        String[] ss = file.split("/");

        String string = "";

        for (int i = 0; i < ss.length; i++) {

            if (ss[i].contains(".")) {

                string = string + ss[i];

                continue;
            } else {

                string = string + ss[i] + "/";
            }

            File f = new File(string);

            if (!f.isDirectory()) {

                f.mkdir();
            }
        }
    }

    /**
     * 将本地文件上传至HDFS文件系统当中
     * @param topic TOPIC名称
     * @param center 目的地
     * @return
     */
    public static void copyToHdfs() {

        String file = local + path;

        if (!new File(file).isDirectory()) {

            System.out.println(new File(file).isDirectory());

            Configuration conf = new Configuration();

            conf.set("fs.defaultFS", hdfs);

            try {

                fs = FileSystem.get(conf);

                fs.copyFromLocalFile(new Path(file), new Path(path));
            } catch (IOException e) {

                e.printStackTrace();
            } finally {

                close();
            }
        }
    }

    /**
     * 关闭写入文件的工具
     */
    public static void close() {

        if (fs != null) {

            try {

                fs.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
