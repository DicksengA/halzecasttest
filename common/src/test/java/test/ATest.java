package test;

import io.vertx.core.json.Json;
import org.junit.jupiter.api.Test;

public class ATest {

    @Test
    public void serialize(){
        PostCreationStatusVO hello = new PostCreationStatusVO("hello",  PostCreationStatus.FAILED, null);
        var value = Json.encode(hello).toString();
        System.out.println(value);
    }
}
