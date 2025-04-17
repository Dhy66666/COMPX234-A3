import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings({"all"})
class Client {
    private final String host;
    private final int port;
    private final String requestFile;

    public Client(String host, int port, String requestFile) {
        this.host = host;
        this.port = port;
        this.requestFile = requestFile;
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(new File(requestFile));
        Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            String part[] =request.split(" ");
            String command = part[0];
            String key = part[1];
            String value = null;
            
        }
    }
}
