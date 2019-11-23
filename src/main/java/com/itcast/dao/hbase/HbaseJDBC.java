package com.itcast.dao.hbase;

import com.itcast.utils.ReadFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 连接HBASE数据库
 */
public class HbaseJDBC {

    private String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/hbaseConf.json");

    private JSONObject jsonObject = new JSONObject(jsonContext);

    public Connection init() throws IOException {

        Configuration configuration = HBaseConfiguration.create();

        configuration.set("hbase.zookeeper.quorum", jsonObject.getString("quorum"));

        configuration.set("hbase.roodir", jsonObject.getString("roodir"));

        Connection connection = ConnectionFactory.createConnection(configuration);

        return connection;
    }
}
