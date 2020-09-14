package ru.innopolis.api;

public interface DemoMap<K, V> {
    /**
     * Запись элемента
     * @param key Ключ
     * @param value Значение
     * @return
     */
    boolean put(K key, V value);

    /**
     * Получение значения по ключу
     * @param key Ключ
     * @return Значение
     */
    V get(K key);

    /**
     * Удаление
     * @param key ключ
     * @return
     */
    boolean remove(K key);

    /**
     * Проверка на наличие ключа
     * @param key Ключ
     * @return Ответ (true/false)
     */
    boolean containsKey(K key);

    /**
     * Размер отображения
     * @return Число
     */
    int size();
}
