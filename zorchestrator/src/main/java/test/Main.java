package test;

import com.hazelcast.cp.internal.datastructures.atomicref.AtomicRef;
import sun.misc.Signal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File(UUID.randomUUID().toString() + ".log");
        Path of = Path.of(".");
        var firstP = new AtomicReference<Process>();
        var secondP = new AtomicReference<Process>();
        Runnable first = () -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder().command("./runfirst.sh")
                        .directory(of.toFile())
                        .inheritIO();
                Process start = processBuilder.start();
                firstP.set(start);
                start.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable second = () -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder().command("./runSecond.sh")
                        .directory(of.toFile())
                        .inheritIO();

                Process start = processBuilder.start();
                secondP.set(start);
                start.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(first).start();
        Thread.sleep(2000);
        new Thread(second).start();
        Signal.handle(new Signal("INT"), sig ->  {
            firstP.get().toHandle().destroyForcibly();
            secondP.get().toHandle().destroyForcibly();
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() ->  {
            firstP.get().toHandle().destroyForcibly();
            secondP.get().toHandle().destroyForcibly();
        }));

    }
}
