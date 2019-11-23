package com.itcast.service;

import com.itcast.service.storm.*;
import com.itcast.utils.ReadFile;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.json.JSONObject;

public class temp {

    public static void main(String[] args) {

        String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/zookeeper.json");

        JSONObject jsonObject = new JSONObject(jsonContext);

        String zkRoot = jsonObject.getString("zkRoot");

        String topic = jsonObject.getString("topic");

        String spoutId = jsonObject.getString("spoutId");

        String zookeeper = jsonObject.getString("zookeeper");

        String topoName = jsonObject.getString("topoName");

        BrokerHosts brokerHosts = new ZkHosts(zookeeper);

        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);

        spoutConfig.scheme = new SchemeAsMultiScheme(new SpoutScheme());

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(spoutId, new KafkaSpout(spoutConfig), 4).setNumTasks(2);

        builder.setBolt("change", new ChangeBolt(), 4).shuffleGrouping(spoutId);

        builder.setBolt("count", new CountBolt(), 4).shuffleGrouping("change");

		builder.setBolt("save", new SaveBolt(), 4).shuffleGrouping("count");

		builder.setBolt("write", new WriteBolt(), 4).shuffleGrouping("count");

        Config conf = new Config();

        conf.setNumWorkers(4);

        conf.setDebug(true);

        conf.setNumAckers(0);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology(topoName, conf, builder.createTopology());
    }
}
