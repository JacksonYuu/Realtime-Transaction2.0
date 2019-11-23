package com.itcast.service.producer;

import com.itcast.utils.ReadFile;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import java.util.*;

/**
 * consumer的配置文件信息
 * @author 拼命三石
 * @version 1.0
 */
public class Configuration implements Runnable {

    //定义消息生产者
    private final KafkaProducer<String, String> producer;

    private String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/producer.json");

    private JSONObject jsonObject = new JSONObject(jsonContext);

    private String[] s = jsonObject.getString("producer").split(",");

    private String server = jsonObject.getString("server");

    private String Topic;

    //在实例化的时候就配置好消息生产者的信息
    public Configuration(String Topic) {

        this.Topic = Topic;

        System.out.println("Topic：" + Topic);

        Properties properties = new Properties();

        //配置Topic的响应端口
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);

        //配置传入参数KEY的值进行序列化操作
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //配置传入参数VALUE的值进行序列化操作
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //创建消息生产者
        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 消息生产者做些什么
     */
    @Override
    public void run() {

        while (true) {

            Random random = new Random();

            int index = random.nextInt(s.length);

            int last = random.nextInt(s.length);

            if (index == last) {

                if (index == s.length - 1) {

                    last --;
                } else {

                    last ++;
                }
            }

            producer.send(new ProducerRecord<String, String>(Topic, s[index] + "-" + s[last]));

            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
