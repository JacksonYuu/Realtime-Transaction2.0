package com.itcast.service.storm;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * 将得到的信息进行计数
 * @author 拼命三石
 * @version 2.0
 */
public class CountBolt implements IBasicBolt {

    private HashMap<String, Integer> message = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        message = new HashMap<String, Integer>();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        String m = input.getString(0);

        String time = input.getString(1);

        String mess = m + ":" + time + "," + m;

        Integer number = message.get(mess);

        if (number == null) {

            number = 0;
        }

        number ++;

        message.put(mess, number);

        collector.emit(new Values(mess, number));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("message", "number"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
