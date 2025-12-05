import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Task4_LogAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename to analyze (e.g., log3.txt): ");
        String filename = sc.nextLine().trim();

        Path p = Paths.get(filename);
        if (!Files.exists(p)) {
            System.out.println("File not found: " + filename);
            sc.close();
            return;
        }

        int total = 0, infoCount = 0, warnCount = 0, errorCount = 0;
        String longestLine = "";
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime firstTS = null, lastTS = null;

        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                total++;

                String u = line.toUpperCase();
                if (u.contains(" INFO ")) infoCount++;
                if (u.contains(" WARN ")) warnCount++;
                if (u.contains(" ERROR ")) errorCount++;

                if (line.length() > longestLine.length()) longestLine = line;

                // try parse first 19 chars as timestamp (yyyy-MM-dd HH:mm:ss)
                if (line.length() >= 19) {
                    String maybeTs = line.substring(0, 19);
                    try {
                        LocalDateTime ts = LocalDateTime.parse(maybeTs, tf);
                        if (firstTS == null || ts.isBefore(firstTS)) firstTS = ts;
                        if (lastTS == null || ts.isAfter(lastTS)) lastTS = ts;
                    } catch (Exception e) {
                        // ignore unparsable timestamp formats
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }

        System.out.println("\n--- Analysis for: " + filename + " ---");
        System.out.println("Total log lines: " + total);
        System.out.println("INFO count: " + infoCount);
        System.out.println("WARN count: " + warnCount);
        System.out.println("ERROR count: " + errorCount);
        System.out.println("Longest log line (" + longestLine.length() + " chars):");
        System.out.println(longestLine.isEmpty() ? "(none)" : longestLine);
        System.out.println("First timestamp: " + (firstTS == null ? "(none parsed)" : firstTS.format(tf)));
        System.out.println("Last timestamp:  " + (lastTS == null ? "(none parsed)" : lastTS.format(tf)));

        sc.close();
    }
}