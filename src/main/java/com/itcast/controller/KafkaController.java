package com.itcast.controller;

import com.itcast.dao.redis.RedisService;
import com.itcast.dao.redis.RedisTool;
import com.itcast.domain.MessageBean;
import com.itcast.utils.ChangeString;
import com.itcast.utils.ReadFile;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Kafka相关控制类
 * @author 拼命三石
 * @version 2.0
 */
@Controller
@RequestMapping("/kafka")
public class KafkaController {

    @RequestMapping("/getMessage")
    @ResponseBody
    public MessageBean getMessage(String key, String date) {

        return new MessageBean(true, new ChangeString().numberToText(key, date));
    }
}
