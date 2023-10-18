package test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GET {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(GET.class);
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(ConfigGenerator.config());
        IMap<String, String> map = hzInstance.<String, String>getMap("hello");
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, event -> {
            logger.info("Map size {}",map.size());
            for (int i = 0; i < 10; i++) {
                var value = map.get(PostCreationStatusVO.key(i));
                logger.info("Value : " + value);
            }
        });
    }
}