import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum Device {
    KEYBOARD(1), MOUSE(2), PRINTER(3);
    int priority;
    Device(int priority) { this.priority = priority; }
}

class InterruptController {
    private final Map<Device, Boolean> mask = new HashMap<>();
    private final List<Device> queue = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public InterruptController() {
        for (Device d : Device.values()) mask.put(d, false);
    }

    public synchronized void triggerInterrupt(Device device) {
        if (mask.get(device)) {
            System.out.println(device + " Ignored (Masked)");
        } else {
            queue.add(device);
            queue.sort(Comparator.comparingInt(d -> d.priority));
            notify();
        }
    }

    public void setMask(Device device, boolean status) {
        mask.put(device, status);
    }

    public void startISRHandler() {
        new Thread(() -> {
            while (true) {
                Device device = null;
                synchronized (this) {
                    while (queue.isEmpty()) {
                        try { wait(); } catch (InterruptedException e) { return; }
                    }
                    device = queue.remove(0);
                }
                handleISR(device);
            }
        }).start();
    }

    private void handleISR(Device device) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(device + " → ISR Completed at " + time);
        log.add(time + " - " + device + " executed");
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public void printLog() {
        System.out.println("\n--- ISR Log ---");
        for (String entry : log) System.out.println(entry);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InterruptController ic = new InterruptController();
        ic.startISRHandler();

        Device[] devices = Device.values();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            ic.triggerInterrupt(devices[rand.nextInt(devices.length)]);
            Thread.sleep(1000);
        }

        Thread.sleep(3000);
        ic.printLog();
    }
}
