package ru.innopolis.main;

public class Main {
    public static void main(String[] args) {

        HashDemoMap hashDemoMap = new HashDemoMap(10);
        hashDemoMap.put("1", "cat");
        hashDemoMap.put(hashDemoMap, "dog");
        hashDemoMap.put(999999999, "yyy");
        hashDemoMap.put(5, "gggg");

        System.out.println(hashDemoMap.get("1"));
        System.out.println(hashDemoMap.get(hashDemoMap));

        hashDemoMap.put("1", "bob");
        System.out.println(hashDemoMap.get("1"));
        hashDemoMap.remove("1");
        System.out.println(hashDemoMap.get("1"));

        System.out.println("max int = " + (1 << 30));
    }
}
