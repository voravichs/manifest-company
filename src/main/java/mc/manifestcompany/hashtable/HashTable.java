package mc.manifestcompany.hashtable;

/**
 * Abstract class for a hash table that supports the insert, remove, and search operations.
 * Adapted from CIT 5940: Data Structures and Software Design zyBooks Activity 6.10
 */

public abstract class HashTable {
    // Returns a non-negative hash code for the specified key.
    protected int hash(Object key) {
        long keyHash = key.hashCode();

        // Java's hashCode() method may return a negative number
        if (keyHash < 0) {
            keyHash += 2147483648L;
        }

        return (int)keyHash;
    }

    // Inserts the specified key/value pair. If the key already exists, the
    // corresponding value is updated. If inserted or updated, true is returned.
    // If not inserted, then false is returned.
    public abstract boolean insert(Object key, Object value);

    // Searches for the specified key. If found, the key/value pair is removed
    // from the hash table and true is returned. If not found, false is returned.
    public abstract boolean remove(Object key);

    // Searches for the key, returning the corresponding value if found, null if
    // not found.
    public abstract Object search(Object key);
}