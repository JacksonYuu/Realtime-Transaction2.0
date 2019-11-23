package com.itcast.service.storm;

import com.itcast.utils.ReadFile;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChangeBolt implements IBasicBolt {

    private String jsonContext = null;

    private JSONObject jsonObject = null;

    private List<String> producer = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/producer.json");

        jsonObject = new JSONObject(jsonContext);

        producer = Arrays.asList(jsonObject.getString("producer").split(","));
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        String message = input.getString(0);

        String time = input.getString(1);

        int first = producer.indexOf(message.split("-")[0]);

        int last = producer.indexOf(message.split("-")[1]);

        message = first + "-" + last;

        collector.emit(new Values(message, time));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("message", "time"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
