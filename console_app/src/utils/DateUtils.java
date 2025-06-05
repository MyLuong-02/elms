package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling date operations.
 */
public class DateUtils {
    private static final String INPUT_FORMAT = "yyyy-MM-dd"; // Expected input format
    private static final String OUTPUT_FORMAT = "MM/dd/yyyy"; // Desired output format
    private static final SimpleDateFormat INPUT_FORMATTER = new SimpleDateFormat(INPUT_FORMAT);
    private static final SimpleDateFormat OUTPUT_FORMATTER = new SimpleDateFormat(OUTPUT_FORMAT);

    /**
     * Parses a date string into a Date object.
     *
     * @param dateStr Date string in format "yyyy-MM-dd".
     * @return Parsed Date object, or null if parsing fails.
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().equalsIgnoreCase("N/A")) {
            return null; // Handle "N/A" case
        }

        try {
            return INPUT_FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + dateStr);
            return null;
        }
    }

    /**
     * Formats a Date object into a string with the format "MM/dd/yyyy".
     *
     * @param date The Date object to format.
     * @return A formatted date string (MM/dd/yyyy), or "N/A" if the date is null.
     */
    public static String formatDate(Date date) {
        return (date != null) ? OUTPUT_FORMATTER.format(date) : "N/A";
    }

/* *
     * Formats a Date object into a string with the format "yyyy-MM-dd" to save to the file.
            *
            * @param date The Date object to format.
            * @return A formatted date string (MM/dd/yyyy), or "N/A" if the date is null.
            */
    public static String formatDateForFile(Date date) {
        return (date != null) ? INPUT_FORMATTER.format(date) : "N/A";
    }

}
