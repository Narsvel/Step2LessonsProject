package lesson1hw;

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

    public int size() { return size; } //возвращает размер MyHashMapRealization

    private int keyHash(Key key) { //вычисляем hash ключа
        return (key == null) ? 0 : key.hashCode(); //если ключ равен null возвращаем 0
    }

    public Value put(Key key, Value value) {
        int keyHash = keyHash(key);
        if (arrayBucket[keyHash % arrayBucket.length] != null) {    //если в ячейке масива есть Bucket
            Bucket bucket = arrayBucket[keyHash % arrayBucket.length];
            return putInBucket(keyHash, key, value, bucket); //функция для прохода по LinkedList бакетов, сравнения и изменения значений Value
        } else {
            arrayBucket[keyHash % arrayBucket.length] = new Bucket<>(keyHash, key, value);
            size++;        //увеличиваем размер MyHashMapRealization
            return value;
        }
    }

    //функция для рекурсивного прохода по LinkedList бакетов, сравнения и изменения значений Value
    private Value putInBucket(int keyHash, Key key, Value value, Bucket bucket) {
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

    public Value get(Key key) {
        int keyHash = keyHash(key);
        if (arrayBucket[keyHash % arrayBucket.length] != null) { //если в ячейке массива есть Bucket
            Bucket bucket = arrayBucket[keyHash % arrayBucket.length];
            return getBucketValue(bucket, keyHash, key); //функция для прохода по LinkedList бакетов и сравнения ключей
        }
        return null; //если ячейка масива null
    }

    //функция для рекурсивного прохода по LinkedList бакетов, сравнения ключей и возвращения значения Value если ключи совпали
    private Value getBucketValue(Bucket bucket, int keyHash, Key key) {
        if (bucket.getHash() == keyHash  && bucket.getKey().equals(key)) { //сравниваем ключи
            return (Value) bucket.getValue();                              //возвращаем значение Value если ключи однинаковые
        } else {
            if (bucket.getNextBucket() != null) {      //провееряем ссылку на следующий бакет
                Bucket nextBucket = bucket.nextBucket; //если есть следующий Bucket
                return getBucketValue(nextBucket, keyHash, key); //рекурсивно вызываем метод getBucketValue
            }
        }
        return null; //если ключ не найден в LinkedList бакетов возвращаем null
    }

    public Value remove(Key key) {
        int keyHash = keyHash(key);
        if (arrayBucket[keyHash % arrayBucket.length] != null) { //если в ячейке массива есть Bucket
            Bucket bucket = arrayBucket[keyHash % arrayBucket.length];
            //проверку первого Bucket производим в этом методе т.к. придется изменить ссылку в ячейке массива при совпадении ключей
                if (bucket.getHash() == keyHash  && bucket.getKey().equals(key)) { //сравниваем ключи
                    if (bucket.nextBucket != null) {
                        arrayBucket[keyHash % arrayBucket.length] = bucket.nextBucket;
                        size--;                                    //уменьшаем размер MyHashMapRealization
                        return (Value) bucket.getValue();
                    } else {
                        arrayBucket[keyHash % arrayBucket.length] = null;
                        size--;                                    //уменьшаем размер MyHashMapRealization
                        return (Value) bucket.getValue();
                    }
                }
                if (bucket.nextBucket != null) {                     //если есть следующий бакет в LinkedList
                    Bucket nextBucket = bucket.nextBucket;           //следующий Bucket пердаем в функцию
                    return removeBuket(bucket, nextBucket, keyHash, key); //функция для прохода по LinkedList бакетов и сравнения ключей
                }
        }
        return null; //если ячейка масива null
    }

    //функция для рекурсивного прохода по LinkedList бакетов, сравнения ключей и удаления бакета
    private Value removeBuket(Bucket beforeBucket, Bucket bucket, int keyHash, Key key) {
        if (bucket.getHash() == keyHash  && bucket.getKey().equals(key)) { //сравниваем ключи
            beforeBucket.nextBucket = bucket.getNextBucket();              //меняем ссылку предыдущего бакета
            size--;                                                        //уменьшаем размер MyHashMapRealization
            return (Value) bucket.getValue();
        } else {
            if (bucket.nextBucket != null) {                               //если есть следующий бакет
                Bucket nextBucket = bucket.nextBucket;
                return removeBuket(bucket, nextBucket, keyHash, key);      //рекурсивно вызываем функцию
            } else return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayBucket.length; i++) { //проходим по массиву arrayBucket
            if (arrayBucket[i] != null) {              //если баккет в ячеке массива не null
                Bucket bucket = arrayBucket[i];
                toStringAddBuckets(bucket, stringBuilder); //метод проходит по LinkedList Bucket в ячейке масива и возвращае их значения в одной строке
                stringBuilder.append("\n");                //перенос строки после прохода по LinkedList Bucket
            }
        }
        return "MyHashMapRealization( \n" + stringBuilder + ')';
    }

    private void toStringAddBuckets(Bucket bucket, StringBuilder stringBuilder) { //метод проходит по Bucket в ячейке масива и возвращае их значения в одной строке
        stringBuilder.append(bucket.toString());
        if (bucket.getNextBucket() != null) {
            toStringAddBuckets(bucket.nextBucket, stringBuilder);
        }
    }

}
