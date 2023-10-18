package test;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

public class ConfigGenerator {

    public static Config config(){
        var config = new Config();
        config.getCPSubsystemConfig()
                .setCPMemberCount(3);
//                .setGroupSize(3);
        config.addMapConfig(new MapConfig("hello").setBackupCount(1));
        return config;
    }
}
