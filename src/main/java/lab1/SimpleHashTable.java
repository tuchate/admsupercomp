package lab1;


import java.util.Iterator;
import java.lang.reflect.Array;

public class SimpleHashTable<K, V> implements ISimpleHashTable<K, V>, Iterable<V> {

    private Entry<K, V>[] array;
    private int count = 0;

    // пара <К V>
    private class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public SimpleHashTable(int size) {
        array = (Entry<K, V>[]) Array.newInstance(Entry.class, size);
        for (int i = 0; i < array.length; i++)
            array[i] = null;
    }

    public SimpleHashTable() {
        this(100);
    }

    public V get(K key) {
        int firstPos = key.hashCode() % array.length;
        //элемент в массиве находится правее хэша, если он вообще там есть
        for (int i = firstPos; i < array.length; i++) {
            if (array[i].key.equals(key)) {
                return array[i].value;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        // если коэффициент заполнения больше половины - ресайзим
        if (count + 1 > array.length / 2)
            resize();
        int firstPos = key.hashCode() % array.length;
        boolean inserted = false;
        // пробуем добавить
        for (int i = firstPos; i < array.length; i++) {
            if (array[i] == null || array[i].key.equals(key)) {
                array[i] = new Entry<K, V>(key, value);
                inserted = true;
                count++;
                break;
            }
        }
        if (!inserted) {
            // если не влезло, ресайзим и рекурсивно запускаем пут
            resize();
            this.put(key, value);
        }
    }

    public V remove(K key) {
        int firstPos = key.hashCode() % array.length;
        // находим и удаляем к чёрту
        for (int i = firstPos; i < array.length; i++) {
            if (array[i].key.equals(key)) {
                V entry = array[i].value;
                array[i] = null;
                count--;
                return entry;
            }
        }
        return null;
    }

    public int size() {
        return count;
    }

    public Iterator<V> iterator() {
        // тут начинается ад
        return new Iterator<V>() {
            private SimpleHashTable<K, V> sht = SimpleHashTable.this;
            private int count = sht.size();
            private int pointer = 0;

            // массив может начинаться с дыры, потому доходим до первого не null
            {
                while(sht.array[pointer] == null && pointer < sht.array.length) {
                    pointer++;
                }
            }

            public boolean hasNext() {
                return count > 0;
            }

            public V next() {
                boolean moved = false;
                // указатель на то что нужно отдать запросившему next
                int prevPointer = pointer;
                // если мы стоим в null, то надо дойти до любого следующего не null
                while(sht.array[pointer] == null && pointer < sht.array.length) {
                    moved = true;
                    pointer++;
                }
                if (!moved) {
                    // если не стояли в null, то всё хорошо, просто берём сл. элемент
                    pointer++;
                } else {
                    // если стояли в null, то надо пройти чуть дальше
                    prevPointer = pointer;
                    pointer++;
                }
                count--;
                return sht.array[prevPointer].value;
            }

            public void remove() {
                // удаляем и оттуда и отсюда и везде
                sht.array[pointer] = null;
                count--;
                sht.count--;
                // сдвигаем указатель вперёд, если оказались в null, идём вперёд пока не найдется что-то нормальное
                pointer++;
                while(pointer < sht.array.length && sht.array[pointer] == null) {
                    pointer++;
                }
            }
        };
    }

    private void resize() {
        count = 0;
        Entry<K, V>[] oldArray = array;
        array = (Entry<K, V>[]) Array.newInstance(Entry.class, oldArray.length * 2);
        for (Entry<K, V> anOldArray : oldArray) {
            if (anOldArray == null)
                continue;
            this.put(anOldArray.key, anOldArray.value);
        }
    }
}
