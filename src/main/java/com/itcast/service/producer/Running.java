package com.itcast.service.producer;

import com.itcast.utils.ReadFile;
import org.json.JSONObject;

/**
 * 将生产者一直运行下去
 */
public class Running {

    public static void main(String[] args) {

        String jsonContext = ReadFile.jsonFile(System.getProperty("user.dir") + "/configuration/zookeeper.json");

        JSONObject jsonObject = new JSONObject(jsonContext);

        new Thread(new Configuration(jsonObject.getString("topic"))).start();
    }
}
