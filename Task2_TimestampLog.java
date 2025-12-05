import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Task2_TimestampLog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename to append timestamp log (e.g., log2.txt): ");
        String filename = sc.nextLine().trim();

        System.out.print("Enter log message: ");
        String message = sc.nextLine();

        // Using BufferedWriter with append mode
        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(filename),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            // exactly as requested in prompt
            bw.write(LocalDateTime.now() + " INFO " + message);
            bw.newLine();

            System.out.println("Timestamped INFO written to " + filename);
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}