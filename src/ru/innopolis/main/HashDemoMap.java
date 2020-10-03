package ru.innopolis.main;

import ru.innopolis.api.DemoMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HashDemoMap implements DemoMap {
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1 << 30;

    private final int initialCapacity;
    private final float loadFactor;
    private int threshold;
    private int size = 0;
    private Node[] hashTable;

    /**
     * Создает пустой {@code HashMap} с указанной начальной
     * вместимостью и коэффициентом загрузки.
     * @param initialCapacity начальная емкость
     * @param loadFactor Фактор коэффициента загрузки
     * @ выдает исключение IllegalArgumentException, если начальная емкость отрицательная
     * или коэффициент загрузки неположительный
     */
    public HashDemoMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.initialCapacity = initialCapacity;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
    * Создает пустую {@code HashMap} с указанным начальным
    * емкость и коэффициент загрузки по умолчанию (0,75).
    *
    * @param initialCapacity - начальная емкость.
    * @ выдает исключение IllegalArgumentException, если начальная емкость отрицательная.
    */
    public HashDemoMap(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        this.initialCapacity = initialCapacity;
        loadFactor = DEFAULT_LOAD_FACTOR;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);

    }

    /**
    * Создает пустую {@code HashMap} с начальной емкостью по умолчанию.
    * (16) и коэффициент нагрузки по умолчанию (0,75).
    */
    public HashDemoMap() {
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
     * Узел базового хеш-бункера, используемый для большинства записей.
     */
    private class Node {
        private final List<Node> nodes;
        private int hash;
        private final Object key;
        private Object value;

        private Node(Object key, Object value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<>();
        }

        private List<Node> getNodes() {
            return nodes;
        }

        private int hash() {
            return hashCode() % hashTable.length;
        }

        private Object getKey() {
            return key;
        }

        private Object getValue() {
            return value;
        }

        private void setValue(Object value) {
            this.value = value;
        }
        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            return hash;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof Node) {
                Node node = (Node) obj;
                return (Objects.equals(key, node.getKey()) &&
                        Objects.equals(value, node.getValue()) ||
                        Objects.equals(hash, node.hashCode()));
            }
            return false;
        }

    }

    /**
     *Связывает указанное значение с указанным ключом на этой карте.
     * Если карта ранее содержала сопоставление для ключа, старый
     * значение заменяется.
     * @param key   ключ, с которым должно быть связано указанное значение
     * @param value значение, которое будет связано с указанным ключом
     * @return результат выполнения
     */
    @Override
    public Object put(Object key, Object value) {
        if (size + 1 >= threshold) {
            threshold *= 2;
            arrayDoubling();
        }

        Node newNode = new Node(key, value);
        int index = newNode.hash();

        if (hashTable[index] == null) {
            return simpleAdd(index, newNode);
        }

        List<Node> nodeList = hashTable[index].getNodes();

        for (Node node : nodeList) {
            if (keyExistButValueNew(node, newNode, value) ||
                    collisionProcessing(node, newNode, nodeList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Добавляет ноду
     * @param index номер бакета
     * @param newNode нода
     * @return результат выполнения
     */
    private boolean simpleAdd(int index, Node newNode) {
        hashTable[index] = new Node(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    private boolean keyExistButValueNew(
            final Node nodeFromList,
            final Node newNode,
            final Object value) {
        if (newNode.getKey().equals(nodeFromList.getKey()) &&
                !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue(value);
            return true;
        }
        return false;
    }

    private boolean collisionProcessing(
            final Node nodeFromList,
            final Node newNode,
            final List<Node> nodes) {

        if (newNode.hashCode() == nodeFromList.hashCode() &&
                !Objects.equals(newNode.key, nodeFromList.key) &&
                !Objects.equals(newNode.value, nodeFromList.value)) {

            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    /**
     * Создаем новый лист с размером в 2 раза больше предыдущего и перезаписываем ноды.
     */
    private void arrayDoubling() {
        Node[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node node : oldHashTable) {
            if (node != null) {
                for (Node n : node.getNodes()) {
                    put(n.key, n.value);
                }
            }
        }
    }

    /**
     * Возвращает значение, которому сопоставлен указанный ключ, или null,
     * если эта карта не содержит сопоставления для ключа.
     *
     * @param key ключ, связанное значение которого должно быть возвращено
     * @return значение, которому сопоставлен указанный ключ,
     * или null, если эта карта не содержит сопоставления для ключа
     */
    @Override
    public Object get(Object key) {
        int index = hash(key);
        if (index < hashTable.length && hashTable[index] != null) {
            List<Node> list = hashTable[index].getNodes();
            for (Node node : list) {
                if (key.equals(node.getKey())) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Удаляет сопоставление для ключа с этой карты, если оно присутствует.
     *
     * @param key ключ, отображение которого нужно удалить с карты.
     * @return предыдущее значение, связанное с ключом, или null,
     * если для ключа не было сопоставления.
     */
    @Override
    public Object remove(Object key) {
        int index = hash(key);
        if (hashTable[index] == null) {
            return false;
        }

        if (hashTable[index].getNodes().size() == 1) {
            hashTable[index].getNodes().remove(0);
            size--;
            return true;
        }

        List<Node> nodeList = hashTable[index].getNodes();
        for (Node node : nodeList) {
            if (key.equals(node.getKey())) {
                nodeList.remove(node);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает true, если эта карта содержит отображение для указанного ключа.
     *
     * @param key - ключ, наличие которого в этой карте необходимо проверить
     * @return истина, если эта карта содержит отображение для указанного ключа
     */
    @Override
    public boolean containsKey(Object key) {
        int index = hash(key);
        if (index < hashTable.length &&
                hashTable[index] != null) {

            List<Node> list = hashTable[index].getNodes();
            for (Node node : list) {
                if (key.equals(node.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Возвращает количество сопоставлений "ключ-значение" на этой карте.
     * Если карта содержит более элементов Integer.MAX_VALUE, возвращает Integer.MAX_VALUE.
     *
     * @return количество сопоставлений "ключ-значение" на этой карте
     */
    @Override
    public int size() {
        return size;
    }

    private int hash(final Object key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }
}
