package ru.innopolis.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MainTest {
    private HashDemoMap<Integer, String> expected = new HashDemoMap<>();
    private         Map<Integer, String> actual = new HashMap<>();


    @Before
    public void setUp() throws Exception {
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
        Assert.assertEquals(expected.get(1), actual.get(1));

        //Проверяем обновление элементов
        expected.put(5, "gggg");
        actual.put(5, "gggg");
        Assert.assertEquals(expected.get(5), actual.get(5));

        //Проверяем метод remove()
        expected.remove(5);
        actual.remove(5);
        Assert.assertEquals(expected.get(5), actual.get(5));

        //Проверяем метод size()
        Assert.assertEquals(expected.size(), actual.size());

        //Проверяем метод containsKey()
        Assert.assertEquals(expected.containsKey(1), actual.containsKey(1));
    }
}