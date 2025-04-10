package lesson1hw;

public class MainTest {

    public static void main(String[] args) {

        MyHashMapRealization<Integer, String> myHashMap = new MyHashMapRealization<>(40);

        for (int i = 0; i < 100; i++) {
            myHashMap.put(i, "Значение" + i);
        }

        System.out.println(myHashMap);

    }
}
