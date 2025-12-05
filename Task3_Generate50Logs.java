import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Task3_Generate50Logs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename to generate 50 logs (e.g., log3.txt): ");
        String filename = sc.nextLine().trim();

        DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] sampleMsgs = {
                "Application Started", "User logged in", "Operation completed",
                "Low memory", "High CPU usage", "Invalid input", "Connection lost",
                "Retrying operation", "Cache cleared", "Configuration loaded"
        };

        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(filename),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            int infoLeft = 25, warnLeft = 15, errorLeft = 10;
            Random rnd = new Random();

            for (int i = 0; i < 50; i++) {
                String level;
                // pick based on remaining counts (randomized order but exact counts)
                int totalLeft = infoLeft + warnLeft + errorLeft;
                int pick = rnd.nextInt(totalLeft);
                if (pick < infoLeft) {
                    level = "INFO";
                    infoLeft--;
                } else if (pick < infoLeft + warnLeft) {
                    level = "WARN";
                    warnLeft--;
                } else {
                    level = "ERROR";
                    errorLeft--;
                }

                String msg = sampleMsgs[rnd.nextInt(sampleMsgs.length)];
                String line = LocalDateTime.now().format(tf) + " " + level + " " + msg;
                bw.write(line);
                bw.newLine();

                // small sleep so timestamps are not identical (optional)
                try { Thread.sleep(10);
                }
                catch (InterruptedException ignored) {}
            }

            System.out.println("50 logs (25 INFO, 15 WARN, 10 ERROR) appended to " + filename);
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}