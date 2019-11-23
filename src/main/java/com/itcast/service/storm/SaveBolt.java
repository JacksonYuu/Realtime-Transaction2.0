package com.itcast.service.storm;

import com.itcast.dao.redis.RedisService;
import com.itcast.dao.redis.RedisTool;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * 将得到的信息存入到REDIS中
 * @author 拼命三石
 * @version 2.0
 */
public class SaveBolt implements IBasicBolt {

    private RedisService redisService;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        redisService = RedisTool.getRedisService();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        String message = input.getString(0);

        Integer number = input.getInteger(1);

        String mid = redisService.get(message);

        if (mid == null) {

            redisService.set(message, String.valueOf(number), 3600000);
        } else {

            number = Integer.valueOf(mid) + number;

            redisService.set(message, String.valueOf(number), 3600000);
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
