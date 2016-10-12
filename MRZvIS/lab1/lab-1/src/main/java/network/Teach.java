package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Teach {
    public static void main(String[] args) throws IOException {
        String image = args[0];
        int tileWidth = Integer.valueOf(args[1]);
        int tileHeight = Integer.valueOf(args[2]);
        int N = Integer.valueOf(args[3]);
        int p = Integer.valueOf(args[4]);
        double step = Double.valueOf(args[5]);
        double accuracy = Double.valueOf(args[6]);

        System.out.println("Image: " + image);
        Thread teachingThread =
                new TeachingThread(N, p, tileWidth, tileHeight, image, "network-" + N + "-" + p + ".json", step, accuracy);
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String command = null;
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if ("stop".equals(command)) {
                        System.out.println("Interupt");
                        teachingThread.interrupt();
                        System.exit(0);
                    }
                }
            }
        }).start();
        teachingThread.start();

    }
}
