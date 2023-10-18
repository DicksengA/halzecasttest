package test;

import com.hazelcast.config.Config;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.EntryEvictedListener;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Evict {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Evict.class);
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(ConfigGenerator.config());
        IMap<String, String> map = hzInstance.<String, String>getMap("hello");
        map.addEntryListener(new EntryEvictedListener<String,String>() {
            @Override
            public void entryEvicted(EntryEvent<String, String> event) {
                logger.info("Evicted entry : {}, {}", event.getKey(), event.getValue());
            }
        }, true);
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, event -> {
//            var value = hzInstance.<String,String>getMap("hello").get("KEY0");
//            logger.info("Value : " + value);
        });

    }
}