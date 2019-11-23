package com.itcast.dao.hbase;

import com.itcast.utils.ReadFile;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * HBASE数据库操作类
 */
public class HbaseService {

    //日志
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/hbaseTable.json");

    private JSONObject jsonObject = new JSONObject(jsonContext);

    private String namespace = jsonObject.getString("namespace");

    private Connection connection = null;

    private Admin admin = null;

    //构造方法
    public HbaseService() {

        HbaseJDBC hbaseJDBC = new HbaseJDBC();

        try {

            connection = hbaseJDBC.init();

            admin = connection.getAdmin();

            this.createNamespace();

            logger.info("连接HBASE数据库成功！");
        } catch (IOException e) {

            logger.info("连接HBASE数据库失败！");
        }
    }

    //关闭连接
    public void close() {

        try {

            if (admin != null) {

                admin.close();
            }

            if (connection != null) {

                connection.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //创建命名空间
    private void createNamespace() {

        try {

            admin.createNamespace(NamespaceDescriptor.create(namespace).build());

            logger.info("创建命名空间成功！");
        } catch (IOException e) {

            logger.info("创建命名空间失败！");
        }
    }

    //创建数据表
    public void createTable() {

        try {

            JSONArray tables = jsonObject.getJSONArray("tables");

            for (int i = 0; i < tables.length(); i++) {

                JSONObject table = tables.getJSONObject(i);

                String tablename = table.getString("tablename");

                TableName tableName = TableName.valueOf(tablename);

                if (!admin.tableExists(tableName)) {

                    HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);

                    String family = table.getString("family");

                    HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);

                    tableDescriptor.addFamily(columnDescriptor);

                    admin.createTable(tableDescriptor);

                    logger.info("数据表" + tablename + "创建成功！");
                }
            }
        } catch (IOException e) {

            logger.info("数据表创建失败！");
        }
    }

    /**
     * 向表中添加数据
     * @param puts 数据列表
     */
    public void insertData(List<Put> puts) {

        JSONArray tables = jsonObject.getJSONArray("tables");

        for (int i = 0; i < tables.length(); i++) {

            JSONObject table = tables.getJSONObject(i);

            String tablename = table.getString("tablename");

            try {

                TableName tableName = TableName.valueOf(tablename);

                Table t = connection.getTable(tableName);

                t.put(puts);

                t.close();

                logger.info("向数据表" + tablename + "添加" + puts.size() + "条数据成功！");
            } catch (Exception e) {

                logger.info("向数据表" + tablename + "添加数据失败！");
            }
        }
    }

    /**
     * 将数据添加到列表中
     * @param puts 列表
     * @param message 数据（以逗号隔开，0-rowkey，1,2-value）
     */
    public List<Put> addPuts(List<Put> puts, String message) {

        JSONArray tables = jsonObject.getJSONArray("tables");

        for (int i = 0; i < tables.length(); i++) {

            JSONObject table = tables.getJSONObject(i);

            String family = table.getString("family");

            JSONArray columns = table.getJSONArray("columns");

            for (int j = 0; j < columns.length(); j++) {

                JSONObject column = columns.getJSONObject(j);

                String col = column.getString("column");

                String[] cols = col.split(",");

                String[] allMessage = message.split(",");

                String rowKey = allMessage[0];

                Put put = new Put(Bytes.toBytes(rowKey));

                for (int k = 0; k < cols.length; k++) {

                    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(cols[k]), Bytes.toBytes(allMessage[k + 1]));
                }

                puts.add(put);
            }
        }

        return puts;
    }
}
