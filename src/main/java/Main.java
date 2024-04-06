import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        var server = new Server("settings.txt");
        server.listen();
    }
}


