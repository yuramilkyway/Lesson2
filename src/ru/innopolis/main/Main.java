package ru.innopolis.main;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashDemoMap<Integer, String> hashDemoMap = new HashDemoMap<>();

        hashDemoMap.put(1, "anime");
        hashDemoMap.put(5, "baba");
        hashDemoMap.put(3, "yyy");
        hashDemoMap.put(5, "gggg");

        System.out.println(hashDemoMap.get(5));
        hashDemoMap.remove(5);
        System.out.println(hashDemoMap.get(5));
    }
}
