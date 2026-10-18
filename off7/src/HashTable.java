import java.util.LinkedList;

class KeyValue{
    String key;
    int value;

    KeyValue(String key, int value){
        this.key = key;
        this.value = value;
    }
}


public class HashTable{
    int size;
    int primeSize;
    LinkedList<KeyValue>[] table;

    public HashTable(int initialSize) {
        size = initialSize;
        primeSize = findNextPrime(size);
        table = new LinkedList[primeSize];
        for (int i = 0; i < primeSize; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Helper function to find the nearest prime greater than or equal to n
    public int findNextPrime(int n){
        int prime = n;
        boolean found = false;

        while (!found){
            boolean isPrime = true;
            for (int i = 2; i <= prime / 2; i++){
                if (prime % i == 0){
                    isPrime = false;
                    break;
                }
            }
            if (isPrime)
                found = true;
            else
                prime++;
        }

        return prime;
    }


    // Hash function 1 (Hash1)
    public int hashFunction1(String key) {
       int hash = Math.abs(key.hashCode()) % primeSize;
       return hash;
    }

    // Hash function 2 (Hash2)
    public int hashFunction2(String key) {
        int hash = 0;
        for (char c : key.toCharArray()) {
            hash = (hash * 17 + c) % primeSize;
        }
        return hash;
    }


    long auxHash(String s) {
        long p = 109;
        long hash_value = 0, p_pow = 1;

        for (char c : s.toCharArray()) {
            hash_value = ((hash_value) + c * p_pow) % primeSize;
            p_pow = (p_pow * p) % primeSize;
        }

        return hash_value;
    }


    // Separate Chaining collision resolution method
    public void separateChainingInsert(String key, int value) {
        int hashValue = hashFunction1(key);
        //int index = hashValue % primeSize;

        LinkedList<KeyValue> list = table[hashValue];
        list.add(new KeyValue(key, value));
    }
    public void separateChainingInsert2(String key, int value) {
        int hashValue = hashFunction2(key);
        //int index = hashValue % primeSize;

        LinkedList<KeyValue> list = table[hashValue];
        list.add(new KeyValue(key, value));
    }


    // Double Hashing collision resolution method
    public void doubleHashingInsert(String key, int value) {
        int hashValue1 = hashFunction1(key);
        int hashValue2 = hashFunction2(key);
        int auxhashValue = (int) auxHash(key);
        //int index = hashValue1 % primeSize;

        for (int i = 0; i < primeSize; i++) {
            int newIndex = (hashValue1 + i * auxhashValue) % primeSize;
            if (table[newIndex].isEmpty()) {
                table[newIndex].add(new KeyValue(key, value));
                return;
            }
        }
    }

    public void doubleHashingInsert2(String key, int value) {
        int hashValue1 = hashFunction1(key);
        int hashValue2 = hashFunction2(key);
        int auxhashValue = (int) auxHash(key);
        //int index = hashValue1 % primeSize;

        for (int i = 0; i < primeSize; i++) {
            int newIndex = (hashValue1 + i * auxhashValue) % primeSize;
            if (table[newIndex].isEmpty()) {
                table[newIndex].add(new KeyValue(key, value));
                return;
            }
        }
    }


    // Custom Probing collision resolution method
    public void customProbingInsert(String key, int value) {
        int hashValue1 = hashFunction1(key);
        int hashValue2 = hashFunction2(key);
        int auxhashValue = (int) auxHash(key);
        //int index = hashValue1 % primeSize;
        int c1 = 1;
        int c2 = 1;

        for (int i = 0; i < primeSize; i++){
            int newIndex = (hashValue2 + c1 * i * auxhashValue + c2 * i * i) % primeSize;
            if (table[newIndex].isEmpty()) {
                table[newIndex].add(new KeyValue(key, value));
                return;
            }
        }
    }


    public void customProbingInsert2(String key, int value) {
        int hashValue1 = hashFunction1(key);
        int hashValue2 = hashFunction2(key);
        int auxhashValue = (int) auxHash(key);
        //int index = hashValue1 % primeSize;
        int c1 = 1;
        int c2 = 1;

        for (int i = 0; i < primeSize; i++){
            int newIndex = (hashValue2 + c1 * i * auxhashValue + c2 * i * i) % primeSize;
            if (table[newIndex].isEmpty()) {
                table[newIndex].add(new KeyValue(key, value));
                return;
            }
        }
    }

    // Insert a key-value pair into the hash table
    public void insert(String key, int value, CollisionResolutionMethod method) {
        switch (method) {
            case SEPARATE_CHAINING:
                separateChainingInsert(key, value);
                break;
            case DOUBLE_HASHING:
                doubleHashingInsert(key, value);
                break;
            case CUSTOM_PROBING:
                customProbingInsert(key, value);
                break;
            case SEPARATE_CHAINING2:
                separateChainingInsert2(key, value);
                break;
            case DOUBLE_HASHING2:
                doubleHashingInsert2(key, value);
                break;
            case CUSTOM_PROBING2:
                customProbingInsert2(key, value);
                break;
        }
    }

    // Find a value for a given key using the specified collision resolution method
    // Find a value for a given key using Hash1
    public int findHash1(String key) {
        return find(key, false);
    }

    // Find a value for a given key using Hash2
    public int findHash2(String key) {
        return find(key, true);
    }

    private int find(String key, boolean useHash2) {
        int hashValue;
        if (useHash2)
            hashValue = hashFunction2(key);
        else
            hashValue = hashFunction1(key);


        int index = hashValue % primeSize;

        LinkedList<KeyValue> list = table[index];
        int probes = 1;

        for (KeyValue keyValue : list){
            if (keyValue.key.equals(key)){
                return probes;
            }
            probes++;
        }

        return probes; // Key not found
    }


    // Enumeration for collision resolution methods
    public enum CollisionResolutionMethod {
        SEPARATE_CHAINING,
        DOUBLE_HASHING,
        CUSTOM_PROBING,
        SEPARATE_CHAINING2,
        DOUBLE_HASHING2,
        CUSTOM_PROBING2
    }

}