package com.itcast.service.storm;

import com.itcast.dao.hbase.HbaseService;
import com.itcast.dao.redis.RedisService;
import com.itcast.dao.redis.RedisTool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将得到的信息存入到HBASE中
 * @author 拼命三石
 * @version 2.0
 */
public class WriteBolt implements IBasicBolt {

    private List<Put> puts = null;

    private HbaseService hbaseService;

    private long time;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        puts = new ArrayList<>();

        hbaseService = new HbaseService();

        hbaseService.createTable();

        time = System.currentTimeMillis();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        String message = input.getString(0);

        String number = String.valueOf(input.getInteger(1));

        long currentTime = System.currentTimeMillis();

        System.out.println(currentTime - time > 500);

        hbaseService.addPuts(puts, message + "," + number);

        if (currentTime - time > 500) {

            hbaseService.insertData(puts);

            time = currentTime;

            puts.clear();
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
