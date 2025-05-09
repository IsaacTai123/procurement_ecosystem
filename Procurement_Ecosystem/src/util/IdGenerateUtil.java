package util;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author tisaac
 */
public class IdGenerateUtil {
    private static final Map<String, Integer> productPrefixCounter = new HashMap<>();

    /**
     * Generate a product ID based on product name.
     * Format: [FIRST_5_LETTERS_IN_UPPERCASE] + [NUMBER_STARTING_FROM_001]
     *
     * Example: "usb cable" → USBCAB-001
     */
    public static String generateProductId(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }

        String cleanedName = name.trim().toUpperCase().replaceAll("\\s+", "");
        String prefix = cleanedName.length() >= 5 ? cleanedName.substring(0, 5) : String.format("%-5s", cleanedName).replace(' ', 'X');

        int count = productPrefixCounter.getOrDefault(prefix, 0) + 1;
        productPrefixCounter.put(prefix, count);

        return prefix + String.format("-%03d", count);
    }

    // Counter to keep track of how many IDs have been generated
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final int MAX_NUMBER_PER_LETTER = 10_000; // 0000 to 9999
    private static final int MAX_LETTERS = 26; // A to Z

    /**
     * Generates the next user ID in sequence.
     * <p>
     * Example outputs: A000, A001, ..., A999, B000, ..., Z999.
     *
     * @return The next sequential user ID string.
     * @throws RuntimeException if the ID exceeds Z999.
     */
    public static String generateUserId() {
        int current = counter.getAndIncrement();

        int letterIndex = current / MAX_NUMBER_PER_LETTER;
        int numberPart = current % MAX_NUMBER_PER_LETTER;

        if (letterIndex >= MAX_LETTERS) {
            throw new RuntimeException("User ID limit exceeded. Reached beyond Z9999.");
        }

        char letter = (char) ('A' + letterIndex);
        String number = String.format("%03d", numberPart);

        return letter + number;
    }

    /**
     * Resets the internal counter to zero.
     * Useful for testing or restarting the sequence.
     */
    public static void reset() {
        counter.set(0);
    }

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateWorkRequestId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }
    
    public static String generateIdByActionAndTimestamp(String action) {
        // Get current timestamp
        long timestamp = System.currentTimeMillis(); // e.g. 1713638400000

        // Optional: Add random digits to avoid duplicates
        int randomFourDigit = new Random().nextInt(9000) + 1000; // 4-digit random number

        return action + "-" + timestamp + "-" + randomFourDigit; // e.g. ORDER-1713638400000-1234
    }
    
    
}
