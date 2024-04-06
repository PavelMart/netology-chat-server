import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private int PORT;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public Server(String settingsPath) throws IOException {
        BufferedReader settingsReader = new BufferedReader(new FileReader(settingsPath));
        PORT = Integer.parseInt(settingsReader.readLine());
        settingsReader.close();
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }




    public void listen() {
        try (
                ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            Logger.log("Сервер чата запущен. Ожидание подключений...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Logger.log("Новое подключение: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
