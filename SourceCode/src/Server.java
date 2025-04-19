import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@SuppressWarnings({"all"})
public class Server {
    private static final int MAX_CLIENTS = 100; // 最多同时处理的客户端数
    private static final TupleSpace tupleSpace = new TupleSpace(); // 共享元组空间

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);

        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

        // 启动统计信息线程
        new Thread(new StatisticsReporter(tupleSpace)).start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected from " + clientSocket.getInetAddress());
                tupleSpace.incrementClientCount(); // 更新客户端计数
                pool.execute(new ClientHandler(clientSocket, tupleSpace)); // 多线程处理客户端
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
