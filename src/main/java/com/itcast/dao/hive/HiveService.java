package com.itcast.dao.hive;

import com.itcast.utils.ReadFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * HIVE数据操作类
 */
public class HiveService {

    //日志类
    private Logger logger = LoggerFactory.getLogger(getClass());

    //读取配置文件信息
    private String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/hiveDatabase.json");

    private JSONObject jsonObject = new JSONObject(jsonContext);

    private String database = jsonObject.getString("database");

    private HiveJDBC jdbc = new HiveJDBC();

    private Connection conn = null;

    private Statement stmt = null;

    private ResultSet rs = null;

    public HiveService() {

        try {

            conn = jdbc.init();

            stmt = conn.createStatement();

            this.createDatabase();

            jdbc.setUrl(jdbc.getUrl() + "/" + database);

            conn = jdbc.init();

            stmt = conn.createStatement();
        } catch (Exception e) {

            logger.info("连接HIVE数据库失败！");
        }
    }

    //关闭连接
    public void close() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //创建数据库
    private void createDatabase() {

        String sql = "create database if not exists " + database;

        try {

            stmt.execute(sql);

            logger.info("创建数据库成功！");
        } catch (SQLException e) {

            logger.info("创建数据库失败！");
        }
    }

    //查询所有数据库
    public List<String> showDatabases() {

        String sql = "show databases";

        try {

            rs = stmt.executeQuery(sql);

            logger.info("查询所有数据库成功！");

            List<String> databases = new ArrayList<>();

            while (rs.next()) {

                databases.add(rs.getString(1));
            }

            return databases;
        } catch (SQLException e) {

            logger.info("查询所有数据库失败！");
        }

        return null;
    }

    //创建数据表
    public void createTable() {

        JSONArray tables = jsonObject.getJSONArray("tables");

        for (int i = 0; i < tables.length(); i++) {

            String sql = "create table if not exists ";

            JSONObject table = tables.getJSONObject(i);

            String tablename = table.getString("tablename");

            sql = sql + tablename + " (";

            JSONArray columns = table.getJSONArray("columns");

            for (int j = 0; j < columns.length(); j++) {

                JSONObject column = columns.getJSONObject(j);

                String name = column.getString("name");

                String type = column.getString("type");

                sql = sql + name + " "  + type;

                if (j == columns.length() - 1) {

                    sql = sql + ")";
                } else {

                    sql = sql + ",";
                }
            }

            sql = sql + " row format delimited fields terminated by \',\' stored as RCFILE";

            try {

                stmt.execute(sql);

                logger.info("数据表" + tablename + "创建成功！");
            } catch (SQLException e) {

                e.printStackTrace();
                logger.info("数据表" + tablename + "创建失败！");
            }
        }
    }

    //查询数据库中的所有数据表
    public List<String> showTables() {

        String sql = "show tables";

        try {

            rs = stmt.executeQuery(sql);

            logger.info("查询所有数据表成功！");

            List<String> tables = new ArrayList<>();

            while (rs.next()) {

                tables.add(rs.getString(1));
            }

            return tables;
        } catch (SQLException e) {

            logger.info("查询所有数据表失败！");
        }

        return null;
    }

    public void loadData(String table, String path) {

        String sql = "load data local inpath \'" + path + "\' into table " + table;

        try {

            logger.info(sql);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
