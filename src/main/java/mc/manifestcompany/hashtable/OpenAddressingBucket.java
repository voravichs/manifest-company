package mc.manifestcompany.hashtable;

/**
 * Represents a single open-addressable bucket in a hash table.
 * Adapted from CIT 5940: Data Structures and Software Design zyBooks Activity 6.10
 */
public class OpenAddressingBucket {
    private final Object key;
    private Object value;

    public static final OpenAddressingBucket EMPTY_SINCE_START =
            new OpenAddressingBucket(null, null);
    public static final OpenAddressingBucket EMPTY_AFTER_REMOVAL =
            new OpenAddressingBucket(null, null);

    public OpenAddressingBucket(Object bucketKey, Object bucketValue) {
        key = bucketKey;
        value = bucketValue;
    }

    public boolean isEmpty() {
        return this == EMPTY_SINCE_START || this == EMPTY_AFTER_REMOVAL;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}