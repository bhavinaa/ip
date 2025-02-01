package bhavs.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Time {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
    private LocalDateTime dateTime;

    public Time(String input, boolean isInteractive) {
        if (isInteractive) {
            this.dateTime = promptForValidDate(input);
        } else {
            this.dateTime = parseDateTime(input);
        }
    }

    // Constructor for file loading
    public Time(String input) {
        this.dateTime = parseDateTime(input);
    }

    private LocalDateTime promptForValidDate(String input) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime parsedDate = null;

        while (parsedDate == null) {
            try {
                input = cleanInput(input);
                parsedDate = LocalDateTime.parse(input, INPUT_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println(" Invalid date format! Please use: yyyy-MM-dd HHmm");
                System.out.println("  Example: 2025-02-15 1800 (Feb 15, 2025, 6:00 PM)");
                System.out.print("Try again: ");
                input = scanner.nextLine();
            }
        }
        return parsedDate;
    }

    private LocalDateTime parseDateTime(String input) {
        try {
            input = cleanInput(input);
            return LocalDateTime.parse(input, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private String cleanInput(String input) {
        input = input.trim(); // Remove extra spaces
        input = input.replace("/", "-");
        return input;
    }

    @Override
    public String toString() {
        return dateTime != null ? dateTime.format(OUTPUT_FORMAT) : "Invalid date";
    }

    public String toFileFormat() {
        return dateTime != null ? dateTime.format(INPUT_FORMAT) : "Invalid date";
    }
}
