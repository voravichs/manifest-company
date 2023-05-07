package mc.manifestcompany.hashtable;

import java.util.Arrays;

/**
 * An abstract class representing an open-addressable hash table.
 * Adapted from CIT 5940: Data Structures and Software Design zyBooks Activity 6.10
 */
public abstract class OpenAddressingHashTable extends HashTable {

    private final OpenAddressingBucket[] table;

    public OpenAddressingHashTable(int initialCapacity) {
        table = new OpenAddressingBucket[initialCapacity];
        Arrays.fill(table, OpenAddressingBucket.EMPTY_SINCE_START);
    }

    protected abstract int probe(Object key, int i);

    // Inserts the specified key/value pair. If the key already exists, the
    // corresponding value is updated. If inserted or updated, true is returned.
    // If not inserted, then false is returned.
    @Override
    public boolean insert(Object key, Object value) {
        // First search for the key in the table. If found, update bucket's value.
        for (int i = 0; i < table.length; i++) {
            int bucketIndex = probe(key, i);

            // An empty-since-start bucket implies the key is not in the table
            if (table[bucketIndex] == OpenAddressingBucket.EMPTY_SINCE_START) {
                break;
            }

            if (table[bucketIndex] != OpenAddressingBucket.EMPTY_AFTER_REMOVAL) {
                // Check if the non-empty bucket has the key
                if (key.equals(table[bucketIndex].getKey())) {
                    // Update the value
                    table[bucketIndex].setValue(value);
                    return true;
                }
            }
        }

        // The key is not in the table, so insert into first empty bucket
        for (int i = 0; i < table.length; i++) {
            int bucketIndex = probe(key, i);
            if (table[bucketIndex].isEmpty()) {
                table[bucketIndex] = new OpenAddressingBucket(key, value);
                return true;
            }
        }

        return false; // no empty bucket found
    }

    // Searches for the specified key. If found, the key/value pair is removed
    // from the hash table and true is returned. If not found, false is returned.
    @Override
    public boolean remove(Object key) {
        for (int i = 0; i < table.length; i++) {
            int bucketIndex = probe(key, i);

            // An empty-since-start bucket implies the key is not in the table
            if (table[bucketIndex] == OpenAddressingBucket.EMPTY_SINCE_START) {
                return false;
            }

            if (table[bucketIndex] != OpenAddressingBucket.EMPTY_AFTER_REMOVAL) {
                // Check if the non-empty bucket has the key
                if (key.equals(table[bucketIndex].getKey())) {
                    // Remove by setting the bucket to empty-after-removal
                    table[bucketIndex] = OpenAddressingBucket.EMPTY_AFTER_REMOVAL;
                    return true;
                }
            }
        }

        return false; // key not found
    }

    // Searches for the key, returning the corresponding value if found, null if
    // not found.
    @Override
    public Object search(Object key) {
        for (int i = 0; i < table.length; i++) {
            int bucketIndex = probe(key, i);

            // An empty-since-start bucket implies the key is not in the table
            if (table[bucketIndex] == OpenAddressingBucket.EMPTY_SINCE_START) {
                return null;
            }

            if (table[bucketIndex] != OpenAddressingBucket.EMPTY_AFTER_REMOVAL) {
                // Check if the non-empty bucket has the key
                if (key.equals(table[bucketIndex].getKey())) {
                    return table[bucketIndex].getValue();
                }
            }
        }

        return null; // key not found
    }

    public OpenAddressingBucket[] getTable() {
        return this.table;
    }
}
