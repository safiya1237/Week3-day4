
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Task1_SimpleLog {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter filename to append (e.g., log1.txt): ");
            String filename = sc.nextLine().trim();

            System.out.print("Enter message to write: ");
            String message = sc.nextLine();

            // FileWriter append = true
            try (FileWriter fw = new FileWriter(filename, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(message);
                bw.newLine();
            }

            System.out.println("Message appended to " + filename);
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }
}