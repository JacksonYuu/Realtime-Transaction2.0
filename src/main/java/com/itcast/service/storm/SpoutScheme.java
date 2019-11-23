package com.itcast.service.storm;

import com.itcast.utils.ReadFile;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 实现Kafka对接Spout传递消息的方案
 * @author 拼命三石
 * @version 2.0
 */
public class SpoutScheme implements Scheme {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public List<Object> deserialize(ByteBuffer ser) {

        Charset charset = Charset.forName("UTF-8");

        String message = charset.decode(ser).toString();

        Date date = new Date();

        String time = sdf.format(date);

        return new Values(message, time);
    }

    @Override
    public Fields getOutputFields() {

        return new Fields("message", "time");
    }
}
