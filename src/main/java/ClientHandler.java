import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public String getName() {
        return username;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public static void handleCloseConnection(String username, ClientHandler handler) {
        try {
            Server.broadcastMessage(username + " отключился", handler);
            Logger.log(handler.clientSocket + " отключился");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        try  {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            username = in.readLine();
            out.println("Добро пожаловать, " + username + "!");
            Server.broadcastMessage("Новое подключение: " + username, this);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Logger.log(username + ": " + inputLine);
                Server.broadcastMessage(username + ": " + inputLine, this);
            }

            clientSocket.close();
            handleCloseConnection(username, this);

        } catch (IOException e) {
            handleCloseConnection(username, this);
        }
    }
}