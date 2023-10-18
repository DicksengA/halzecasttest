package test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PUT {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(PUT.class);
        AtomicInteger atomicInteger = new AtomicInteger();
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(ConfigGenerator.config());
        IMap<String, String> map = hzInstance.<String, String>getMap("hello");
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, event -> {
            int i = atomicInteger.incrementAndGet() %10;
            map.put(PostCreationStatusVO.key(i), Json.encode(new PostCreationStatusVO(""+i, PostCreationStatus.FAILED,null)), 9, TimeUnit.SECONDS);
            logger.info("Put");
        });

    }
}