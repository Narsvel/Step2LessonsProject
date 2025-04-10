package lesson1hw;

public class MainTest {

    public static void main(String[] args) {

        MyHashMapRealization<Integer, String> myHashMap = new MyHashMapRealization<>(40);

        System.out.println(myHashMap.isEmpty());

        for (int i = 0; i < 100; i++) {
            myHashMap.put(i, "Значение" + i);
        }

        System.out.println(myHashMap);
        System.out.println(myHashMap.size());
        System.out.println(myHashMap.isEmpty());

        System.out.println(myHashMap.get(30));

        System.out.println(myHashMap.remove(28));
        System.out.println(myHashMap.size());
        System.out.println(myHashMap.get(28));

    }
}
