package interfaces;

/**
 * Interface to be implemented by any class whose instances
 * can be uniquely identified by a String ID.
 *
 * <p>This interface is commonly used to provide a standard way
 * for utility methods (e.g., searching in a list) to access an object's identifier.</p>
 *
 * @Author tisaac
 */
public interface Identifiable {
    /**
     * Returns the unique identifier for this object.
     *
     * @return the ID as a String
     */
    String getId();
}
