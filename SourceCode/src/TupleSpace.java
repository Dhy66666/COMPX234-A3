import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TupleSpace {
    private final ConcurrentHashMap<String, String> space = new ConcurrentHashMap<>();

    // 统计信息
    private final AtomicInteger totalOps = new AtomicInteger(0);
    private final AtomicInteger putCount = new AtomicInteger(0);
    private final AtomicInteger getCount = new AtomicInteger(0);
    private final AtomicInteger readCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final AtomicInteger clientCount = new AtomicInteger(0);

    public synchronized String put(String key, String value) {
        totalOps.incrementAndGet();
        putCount.incrementAndGet();

        if (space.containsKey(key)) {
            errorCount.incrementAndGet();
            return "ERR " + key + " already exists";
        }

        space.put(key, value);
        return "OK (" + key + ", " + value + ") added";
    }

    public synchronized String get(String key) {
        totalOps.incrementAndGet();
        getCount.incrementAndGet();

        if (!space.containsKey(key)) {
            errorCount.incrementAndGet();
            return "ERR " + key + " does not exist";
        }

        String value = space.remove(key);
        return "OK (" + key + ", " + value + ") removed";
    }

    public synchronized String read(String key) {
        totalOps.incrementAndGet();
        readCount.incrementAndGet();

        if (!space.containsKey(key)) {
            errorCount.incrementAndGet();
            return "ERR " + key + " does not exist";
        }

        String value = space.get(key);
        return "OK (" + key + ", " + value + ") read";
    }
    public synchronized int size() {
        return space.size();
    }

    public synchronized double avgKeySize() {
        return space.keySet().stream().mapToInt(String::length).average().orElse(0);
    }

    public synchronized double avgValueSize() {
        return space.values().stream().mapToInt(String::length).average().orElse(0);
    }

    public synchronized double avgTupleSize() {
        return space.entrySet().stream()
                .mapToInt(entry -> entry.getKey().length() + entry.getValue().length())
                .average().orElse(0);
    }
    public int getTotalOps() {
        return totalOps.get();
    }

    public int getPutCount() {
        return putCount.get();
    }

    public int getGetCount() {
        return getCount.get();
    }

    public int getReadCount() {
        return readCount.get();
    }

    public int getErrorCount() {
        return errorCount.get();
    }

    public int getClientCount() {
        return clientCount.get();
    }

    public void incrementClientCount() {
        clientCount.incrementAndGet();
    }
}
