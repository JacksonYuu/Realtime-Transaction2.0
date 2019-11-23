package com.itcast.dao.hive;

import com.itcast.utils.ReadFile;
import org.json.JSONObject;

import java.sql.*;

/**
 * 连接HIVE数据库
 */
public class HiveJDBC {

    //读取配置文件信息
    private String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/hiveJDBC.json");

    private JSONObject jsonObject = new JSONObject(jsonContext);

    private String driverName = jsonObject.getString("driverName");

    private String url = jsonObject.getString("url");

    private String user = jsonObject.getString("user");

    private String password = jsonObject.getString("password");

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 加载驱动、创建连接
    public Connection init() throws Exception {

        Class.forName(driverName);

        Connection conn = DriverManager.getConnection(url,user,password);

        return conn;
    }
}
