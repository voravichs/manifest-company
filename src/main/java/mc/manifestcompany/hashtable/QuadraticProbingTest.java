package mc.manifestcompany.hashtable;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuadraticProbingTest {
    @Test
    public void test() {
        QuadraticProbingHashTable qpHash = new QuadraticProbingHashTable();
        assertTrue(qpHash.insert("test", 1));
        qpHash = new QuadraticProbingHashTable(1, 1, 3);

        // Insert
        assertTrue(qpHash.insert("test", 1));
        assertTrue(qpHash.insert("testing", 2));
        assertTrue(qpHash.insert("testtest", 3));
        assertFalse(qpHash.insert("failure", 4));
        assertEquals(3, qpHash.getTable().length);

        // Remove
        assertTrue(qpHash.remove("test"));
        List<OpenAddressingBucket> bucketList = List.of(qpHash.getTable());
        for (OpenAddressingBucket bucketItem: bucketList) {
            assertNotEquals("test", bucketItem.getKey());
        }

        // Search and re-insert
        assertTrue(qpHash.insert("test", 1));
        assertEquals(1, qpHash.search("test"));
        assertTrue(qpHash.insert("test", 3));
        assertEquals(3, qpHash.search("test"));

        // Probe
        assertEquals(1, qpHash.probe("test", 0));
        assertTrue(qpHash.remove("test"));
        assertTrue(qpHash.insert("test", 1));
        assertEquals(1, qpHash.probe("test", 0));


    }
}
