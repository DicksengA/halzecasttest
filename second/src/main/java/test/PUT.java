package test;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PUT {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(PUT.class);
        AtomicInteger atomicInteger = new AtomicInteger();
        var config = new Config();
        config.addMapConfig(new MapConfig("hello").setBackupCount(1));
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
        IMap<String, String> map = hzInstance.<String, String>getMap("hello");
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, event -> {
            hzInstance.getMap("hello").put("world", "" + atomicInteger.incrementAndGet(), 1, TimeUnit.SECONDS);
            logger.info("Put");
        });

    }
}