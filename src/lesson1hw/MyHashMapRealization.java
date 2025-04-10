package lesson1hw; //get, put, remove

public class MyHashMapRealization<Key, Value> {

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

        public Bucket(int hash, Key key, Value value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
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

        @Override
        public String toString() {
            return "{key=" + key + ", value=" + value + "}; ";
        }

    }

    public boolean isEmpty() {
        return size == 0;
    }  //проверка пустой ли MyHashMapRealization

    private int keyHash(Key key) { //вычисляем hash ключа
        return (key == null) ? 0 : key.hashCode(); //если ключ равен null возвращаем 0
    }

    public Value put(Key key, Value value) {
        int keyHash = keyHash(key);
        if (arrayBucket[keyHash % arrayBucket.length] != null) {    //если в ячейке масива есть Bucket
            Bucket bucket = arrayBucket[keyHash % arrayBucket.length];
            return putInBucket(keyHash, key, value, bucket); //функция для прохода по LinkedList бакетов, сравнения и изменения значений Value
        } else { //остается проблема в других Bucket массива arrayBucket могут быть другие Classы Key и Value !!!!!!!!!!!!!!!!!!!!
            arrayBucket[keyHash % arrayBucket.length] = new Bucket<>(keyHash, key, value);
            size++;        //увеличиваем размер MyHashMapRealization
            return value;
        }
    }

    private Value putInBucket(int keyHash, Key key, Value value, Bucket bucket) { //функция для прохода по LinkedList бакетов, сравнения и изменения значений Value
        if (bucket.getHash() == keyHash  && bucket.getKey().equals(key)) { //если Hash и  Equals true изменяем значение Value в Bucket
            Value oldValue = (Value) bucket.getValue();
            bucket.setValue(value);
            return oldValue; //возвращаем старое значение Value
        } else {
            if (bucket.getNextBucket() != null) {
                Bucket nextBucket = bucket.nextBucket; //если есть следующий Bucket
                return putInBucket(keyHash, key, value, nextBucket); //рекурсивно вызываем функцию прохода по LinkedList бакетов
            } else {
                Bucket newBucket = new Bucket(keyHash, key, value); //если следующего Bucket нет создаем новый Bucket
                bucket.setNextBucket(newBucket); //сохраняем ссылку на него в предыдущем Bucket
                size++;        //увеличиваем размер MyHashMapRealization
                return value;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayBucket.length; i++) {
            Bucket bucket = arrayBucket[i];
            stringBuilder.append(toStringAddBuckets(bucket));
            stringBuilder.append("\n");
        }
        return "MyHashMapRealization( \n" + stringBuilder + ')';
    }

    private String toStringAddBuckets(Bucket bucket) { //метод проходит по Bucket в ячейке масива и возвращае их значения в одной строке
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bucket.toString());
        if (bucket.getNextBucket() != null) {
            stringBuilder.append(toStringAddBuckets(bucket.nextBucket));
        }
        return stringBuilder.toString();
    }

}
