package playback;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LavalinkProcess {

    private Process process;
    private final CountDownLatch readyLatch = new CountDownLatch(1);

    public void start() throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-jar",
                "Lavalink.jar"
        );

        pb.directory(new File("lavalink/"));
        pb.redirectErrorStream(true); // merge stderr + stdout so we don’t miss logs

        process = pb.start();
        System.out.println("[Lavalink] started");

        // Start log reader
        startLogReader(process.getInputStream());

        // BLOCK until Lavalink signals readiness
        boolean ready = readyLatch.await(60, TimeUnit.SECONDS);

        if (!ready) {
            throw new RuntimeException("Lavalink failed to start within timeout");
        }

        System.out.println("[Lavalink] ready");
    }

    private void startLogReader(InputStream inputStream) {
        Thread t = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {

                    System.out.println("[Lavalink] " + line);

                    // READY SIGNAL (this is the important part)
                    if (line.contains("Lavalink is ready to accept connections")) {
                        readyLatch.countDown();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        t.setDaemon(true);
        t.start();
    }

    public void stop() {
        if (process != null && process.isAlive()) {
            process.destroy();
            System.out.println("[Lavalink] stopped");
        }
    }

    public boolean isRunning() {
        return process != null && process.isAlive();
    }
}