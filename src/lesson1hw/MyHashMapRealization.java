package lesson1hw;

public class MyHashMapRealization {

    private static final int DEFAULT_CAPACITY = 16;
    private int size;

    static class Bucket<K,V> {
        final int hash;
        final K key;
        V value;
        //реализация LinkedList, ссыска на следующий Bucket
        Bucket<K, V> nextBucket;

        public Bucket(int hash, K key, V value, Bucket<K, V> nextBucket) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextBucket = nextBucket;
        }

        public int getHash() {
            return hash;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Bucket<K, V> getNextBucket() {
            return nextBucket;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setNextBucket(Bucket<K, V> nextBucket) {
            this.nextBucket = nextBucket;
        }

        @Override
        public final boolean equals(Object object) {
            if (!(object instanceof Bucket<?, ?> bucket)) return false;

            return key.equals(bucket.key) && value.equals(bucket.value);
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    public MyHashMapRealization() {

    }





}
