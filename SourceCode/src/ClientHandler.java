import java.io.*;
import java.net.Socket;
@SuppressWarnings({"all"})
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final TupleSpace tupleSpace;

    public ClientHandler(Socket socket, TupleSpace tupleSpace) {
        this.socket = socket;
        this.tupleSpace = tupleSpace;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;

            while ((line = in.readLine()) != null) {
                if (line.length() < 5) {
                    out.println("010 ERR invalid format");
                    continue;
                }

                // 去掉长度前缀
                String request = line.substring(4);  // 跳过前 3 个长度数字 + 空格
                String[] parts = request.split(" ", 3);  // 限制最多三部分
                String command = parts[0];
                String key = parts.length > 1 ? parts[1] : "";
                String value = parts.length > 2 ? parts[2] : "";

                String result;

                switch (command) {
                    case "P":
                        result = tupleSpace.put(key, value);
                        break;
                    case "G":
                        result = tupleSpace.get(key);
                        break;
                    case "R":
                        result = tupleSpace.read(key);
                        break;
                    default:
                        result = "ERR unknown command";
                        break;
                }

                // 加上长度前缀后发送回客户端
                String response = String.format("%03d %s", result.length() + 4, result);
                out.println(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
