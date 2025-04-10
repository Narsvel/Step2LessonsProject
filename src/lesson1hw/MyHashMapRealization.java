package lesson1hw;

public class MyHashMapRealization {

    private static final int DEFAULT_CAPACITY = 16; //DEFAULT размер внутреннего массива
    private int size;                   //размер MyHashMapRealization
    private final Bucket[] arrayBucket; //внутренний масиив бакетов

    public MyHashMapRealization() {
        arrayBucket = new Bucket[DEFAULT_CAPACITY];
    }

    public MyHashMapRealization(int capacity) {
        arrayBucket = new Bucket[capacity];
    }

    private static class Bucket<Key,Value> {
        final int hash;
        final Key key;
        Value value;
        //реализация LinkedList, ссыска на следующий Bucket
        Bucket<Key, Value> nextBucket;

        public Bucket(int hash, Key key, Value value, Bucket<Key, Value> nextBucket) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextBucket = nextBucket;
        }

        public int getHash() {
            return hash;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Bucket<Key, Value> getNextBucket() {
            return nextBucket;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public void setNextBucket(Bucket<Key, Value> nextBucket) {
            this.nextBucket = nextBucket;
        }

    }


}
