package com.itcast.dao.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Service
public class RedisService extends AbstractRedisService<String, String> {

    public HashMap<String, String> scan(final String key) {

        return super.getRedisTemplate().execute(new RedisCallback<HashMap<String, String>>() {

            @Override
            public HashMap<String, String> doInRedis(RedisConnection connection) {

                HashMap<String, String> allMessage = new HashMap<>();

                ScanOptions options = ScanOptions.scanOptions().match(key).count(Integer.MAX_VALUE).build();

                Cursor<byte[]> scan = connection.scan(options);

                while (scan.hasNext()) {

                    byte[] bytes = scan.next();

                    byte[] values = connection.get(bytes);

                    try {

                        allMessage.put(new String(bytes, "UTF-8"), new String(values, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }

                return allMessage;
            }
        });
    }
}
