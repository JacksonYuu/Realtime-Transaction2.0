package com.itcast.dao.hbase;

import org.apache.hadoop.hbase.client.Put;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 */
public class temp {

    public static void main(String[] args) {

        HbaseService hbaseService = new HbaseService();

        hbaseService.createTable();

        List<Put> puts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            hbaseService.addPuts(puts, i + ",send-receive," + i);
        }

        hbaseService.insertData(puts);

        hbaseService.close();
    }
}
