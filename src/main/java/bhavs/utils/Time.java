package bhavs.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The {@code Time} class provides utilities for parsing, validating,
 * and formatting date-time strings in different formats.
 */
public class Time {
    /** Formatter for user input format: yyyy-MM-dd HHmm */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Formatter for display output format: MMM dd yyyy, hh:mm a */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");

    /** Stores the parsed date-time value */
    private LocalDateTime dateTime;

    private String storeErrorMessage = null;

    /**
     * Constructs a {@code Time} object by parsing the input string.
     * If interactive mode is enabled, the user is prompted for valid input if needed.
     *
     * @param input         The date-time string to be parsed.
     * @param isInteractive {@code true} to enable interactive mode for validation, {@code false} to parse directly.
     */
    public Time(String input, boolean isInteractive) {
        if (isInteractive) {
            this.dateTime = promptForValidDate(input);
        } else {
            this.dateTime = parseDateTime(input);
        }
    }

    /**
     * Constructs a {@code Time} object for file loading.
     *
     * @param input The date-time string to be parsed.
     */
    public Time(String input) {
        this.dateTime = parseDateTime(input);
    }

    /**
     * Prompts the user until a valid date-time input is provided.
     *
     * @param input The initial date-time string.
     * @return A valid {@code LocalDateTime} object.
     */
    private LocalDateTime promptForValidDate(String input) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime parsedDate = null;

        while (parsedDate == null) {
            try {
                input = cleanInput(input);
                parsedDate = LocalDateTime.parse(input, INPUT_FORMAT);
            } catch (DateTimeParseException e) {
                String error = "Invalid date format! Please use: yyyy-MM-dd HHmm/n" +
                        "Example: 2025-02-15 1800 (Feb 15, 2025, 6:00 PM)";
                storeErrorMessage = error;
                 System.out.println("Invalid date format! Please use: yyyy-MM-dd HHmm");
                 System.out.println("Example: 2025-02-15 1800 (Feb 15, 2025, 6:00 PM)");
                // System.out.print("Try again: ");
                // input = scanner.nextLine();
                return null;
            }
        }
        return parsedDate;
    }

    public String getErrorMessage() {
        return this.storeErrorMessage;
    }
    /**
     * Parses a date-time string into a {@code LocalDateTime} object.
     *
     * @param input The date-time string to be parsed.
     * @return A {@code LocalDateTime} object, or {@code null} if parsing fails.
     */
    private LocalDateTime parseDateTime(String input) {
        try {
            input = cleanInput(input);
            return LocalDateTime.parse(input, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("You have given the wrong format IN PARSE DATA TIME!");
            storeErrorMessage = "You have given the wrong format";
            return null;
        }
    }
    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }


    /**
     * Cleans the input string by trimming spaces and replacing slashes with dashes.
     *
     * @param input The raw input string.
     * @return A cleaned input string.
     */
    private String cleanInput(String input) {
        input = input.trim();
        input = input.replace("/", "-");
        return input;
    }

    /**
     * Converts the stored date-time into a human-readable format.
     *
     * @return A formatted date-time string, or "Invalid date" if parsing failed.
     */
    @Override
    public String toString() {
        return dateTime != null ? dateTime.format(OUTPUT_FORMAT) : "Invalid date";
    }

    /**
     * Converts the stored date-time into a file-compatible format.
     *
     * @return A date-time string formatted for file storage, or "Invalid date" if parsing failed.
     */
    public String toFileFormat() {
        return dateTime != null ? dateTime.format(INPUT_FORMAT) : "Invalid date";
    }
}
