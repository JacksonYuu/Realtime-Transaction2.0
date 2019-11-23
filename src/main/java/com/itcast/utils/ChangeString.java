package com.itcast.utils;

import com.itcast.dao.redis.RedisService;
import com.itcast.dao.redis.RedisTool;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 改变字符串
 * @author 拼命三石
 * @version 2.0
 */
public class ChangeString {

    /**
     * 将数字改变成文字
     * @param key 需要修改的数字
     * @return 返回改变之后的字符串
     */
    public static String numberToText(String key, String date) {

        String path = "D:\\IntelliJ IDEA 2018.2.4-workspace\\realtime-transaction2.0\\configuration\\producer.json";

        String jsonContext = ReadFile.jsonFile(path);

        JSONObject jsonObject = new JSONObject(jsonContext);

        String[] producer = jsonObject.getString("producer").split(",");

        String matcher = "*-" + key + ":" + date + "*";

        RedisService redisService = RedisTool.getRedisService();

        HashMap<String, String> allMessage = redisService.scan(matcher);

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("[");

        for (Map.Entry<String, String> m : allMessage.entrySet()) {

            String send = producer[Integer.valueOf(m.getKey().split("-")[0])];

            String receive = producer[Integer.valueOf(key) - 1];

            if (send.equals(receive)) {

                continue;
            }

            stringBuffer.append("[{name:\'" + send);

            stringBuffer.append("\',value:" + m.getValue() + "},");

            stringBuffer.append("{name:\'" + receive + "\'}],");
        }

        if (stringBuffer.length() > 0) {

            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        stringBuffer.append("]");

        return stringBuffer.toString();
    }
}
