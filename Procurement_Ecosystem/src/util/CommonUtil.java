package util;

import interfaces.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * @author tisaac
 */
public class CommonUtil {

    /**
     * Searches for an item in the given list by its unique identifier.
     *
     * @param <T>   the type of objects in the list, which must implement {@code Identifiable}
     * @param list  the list of items to search
     * @param id    the ID to search for
     * @return an {@code Optional} containing the item if found, or {@code Optional.empty()} if not
     */
    public static <T extends Identifiable> Optional<T> findById(List<T> list, String id) {
        for (T item : list) {
            if (item.getId().equals(id)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
