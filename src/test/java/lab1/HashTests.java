package lab1;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class HashTests extends Assert {
    @Test
    public void testInsert() {
        SimpleHashTable<String, String> hash = new SimpleHashTable<String, String>(10);
        hash.put("Olo", "lo");
        assertEquals("lo", hash.get("Olo"));
        for (int i = 0; i < 3; i++) {
            hash.put(Integer.toString(i), Integer.toString(i));
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(hash.get(Integer.toString(i)), Integer.toString(i));
        }
        assertEquals("lo", hash.get("Olo"));
    }

    @Test
    public void testRemove() {
        SimpleHashTable<String, String> hash = new SimpleHashTable<String, String>(10);
        hash.put("Olo", "lo");
        assertEquals("lo", hash.get("Olo"));
        assertEquals("lo", hash.remove("Olo"));
        assertEquals(null, hash.get("Olo"));
        for (int i = 0; i < 20; i++) {
            hash.put(Integer.toString(i), Integer.toString(i));
        }
        assertEquals(hash.size(), 20);
        for (int i = 0; i < 20; i++) {
            assertEquals(hash.remove(Integer.toString(i)), Integer.toString(i));
            assertEquals(hash.remove(Integer.toString(i)), null);
        }
    }

    @Test
    public void iteratorTest() {
        SimpleHashTable<String, String> hash = new SimpleHashTable<String, String>(10);
        Set<String> set = new HashSet<String>();
        for(int i = 0; i < 100; i++) {
            set.add(Integer.toString(i));
            hash.put(Integer.toString(i), Integer.toString(i));
        }
        for(String s : hash) {
            assertTrue(set.contains(s));
            set.remove(s);
        }
        assertEquals(hash.size(), 100);
        assertEquals(0, set.size());
        while(hash.size() > 0) {
            Iterator<String> it = hash.iterator();
            while (it.hasNext()) {
                if (new Random().nextBoolean())
                    assertTrue(true);
                else
                    it.remove();
            }
        }
        assertEquals(hash.size(), 0);
    }
}
