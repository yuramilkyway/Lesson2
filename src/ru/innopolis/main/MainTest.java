package ru.innopolis.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MainTest {
    private final HashDemoMap expected = new HashDemoMap();
    private final Map actual = new HashMap();


    @Before
    public void setUp() {
        //Создаем тестовые данные
        expected.put(1, "anime");
        expected.put(5, "baba");
        expected.put(3, "yyy");

        //Создаем данные для сравнения
        actual.put(1, "anime");
        actual.put(5, "baba");
        actual.put(3, "yyy");
    }

    @Test
    public void main() {
        //Запускаем тест
        //Провяем метод put() и get()
        Assert.assertEquals(actual.get(1), expected.get(1));

        //Проверяем обновление элементов
        expected.put(5, "gggg");
        actual.put(5, "gggg");
        Assert.assertEquals(actual.get(5), expected.get(5));

        //Проверяем метод remove()
        expected.remove(5);
        actual.remove(5);
        Assert.assertEquals(actual.get(5), expected.get(5));

        //Проверяем метод size()
        Assert.assertEquals(actual.size(), expected.size());

        //Проверяем метод containsKey()
        Assert.assertEquals(actual.containsKey(1), expected.containsKey(1));
        }
}