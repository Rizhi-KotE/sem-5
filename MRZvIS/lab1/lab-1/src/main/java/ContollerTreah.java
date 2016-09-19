import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ControllerTreahd extends Thread {

    private Thread thread;

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String command = null;
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ("stop".equals(command)) {
                    thread.interrupt();
                    System.exit(0);
                }
            } finally {
                thread.interrupt();
            }
        }
    }
}
