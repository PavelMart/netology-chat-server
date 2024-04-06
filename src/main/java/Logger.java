import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    public static void log(String message) throws IOException {
        try (
                BufferedWriter logWriter = new BufferedWriter(new FileWriter("file.log", true));
        ) {
            System.out.println("[" + new Date() + "] " + message);
            logWriter.write("[" + new Date() + "] " + message + "\n");
            logWriter.flush();
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
