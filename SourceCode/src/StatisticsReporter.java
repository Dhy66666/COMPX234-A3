@SuppressWarnings({"all"})
public class StatisticsReporter implements Runnable {
    private final TupleSpace tupleSpace;

    public StatisticsReporter(TupleSpace tupleSpace) {
        this.tupleSpace = tupleSpace;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000); // 每 10 秒执行一次
            } catch (InterruptedException e) {
                System.out.println("Statistics reporter interrupted.");
                return;
            }

            System.out.println("========== Tuple Space Statistics ==========");
            System.out.println("Tuple count       : " + tupleSpace.size());
            System.out.println("Avg tuple size    : " + String.format("%.2f", tupleSpace.avgTupleSize()));
            System.out.println("Avg key size      : " + String.format("%.2f", tupleSpace.avgKeySize()));
            System.out.println("Avg value size    : " + String.format("%.2f", tupleSpace.avgValueSize()));
            System.out.println("Client count      : " + tupleSpace.getClientCount());
            System.out.println("Total operations  : " + tupleSpace.getTotalOps());
            System.out.println("Total PUTs        : " + tupleSpace.getPutCount());
            System.out.println("Total GETs        : " + tupleSpace.getGetCount());
            System.out.println("Total READs       : " + tupleSpace.getReadCount());
            System.out.println("Total ERRs        : " + tupleSpace.getErrorCount());
            System.out.println("============================================");
        }
    }
}
