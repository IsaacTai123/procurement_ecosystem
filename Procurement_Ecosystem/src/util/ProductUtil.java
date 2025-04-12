package util;

import model.procurement.PurchaseItem;
import model.product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tisaac
 */
public class ProductUtil {
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
}
