import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new LinkedList<>();

        l1.add("John");
        l2.add("John");

        System.out.println(l1.equals(l2));

        List<Object> lo = new ArrayList<>();
        List<String> ls = new ArrayList<>();
//        lo = ls; //ошибка при типизации

        Collections collections;
        Set<String> l3 = new HashSet<>();
        l3.add("John");
        System.out.println(l1.equals(l3));

    }
}