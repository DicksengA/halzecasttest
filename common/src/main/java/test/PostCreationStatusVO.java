package test;

import java.util.Map;

public record PostCreationStatusVO(String mHostname,
                                   PostCreationStatus status,
                                   Map<String, String> killReason
) {
    public static String key(int i){
        return "KEY"+i;
    }
}