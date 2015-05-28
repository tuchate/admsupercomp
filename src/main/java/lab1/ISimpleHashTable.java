package lab1;

public interface ISimpleHashTable<K, V> {
    /**
     * Взять элемент по ключу
     * @param key
     * @return
     */
    V get(K key);

    /**
     * Положить в таблицу объект по адресу ключа
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * Удалить элемент с ключом. Если запись есть, то вернуть ссылку на объект. Если нет,
     * то вернуть null
     * @param key
     * @return
     */
    V remove(K key);

    /**
     * Вернуть количество элементов в таблице
     * @return
     */
    int size();
}
