package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;
import java.util.function.Supplier;

public class ChainingHashDictionary<K, V> implements IDictionary<K, V> {
    private Supplier<IDictionary<K, V>> chain;
    private int nextPrime = 0;
    private int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 725147};
    private Object[] hashTable;
    private static final double load = 0.5;
    private int size;

    public ChainingHashDictionary(Supplier<IDictionary<K, V>> chain) {
        this.chain = chain;
        this.hashTable = new Object[this.primes[this.nextPrime]];
        this.size = 0;
    }

    /**
     * @param key
     * @return value corresponding to key
     */
    @Override
    public V get(K key) {
        int hash = Math.abs(key.hashCode() % this.hashTable.length);
        IDictionary<K, V> value = (IDictionary<K, V>) this.hashTable[hash];
        if (value == null){
            return null;
        }
        return value.get(key);
    }

    @Override
    public V remove(K key) {
        int hash = Math.abs(key.hashCode() % this.hashTable.length);
        IDictionary<K, V> value = (IDictionary<K, V>) this.hashTable[hash];
        if (value == null || !value.containsKey(key)){
            return null;
        }
        this.size--;
        return value.remove(key);
    }

    private void resize(){
        this.nextPrime++;
        Object[] newHash = new Object[this.primes[nextPrime]];
        for (Object dic : this.hashTable){
            if (dic != null) {
                for (K get : ((IDictionary<K, V>) dic).keySet()) {
                    int h = Math.abs(get.hashCode() % newHash.length);
                    if (newHash[h] == null) {
                        newHash[h] = chain.get();
                    }
                    ((IDictionary<K, V>) newHash[h]).put(get, ((IDictionary<K, V>) dic).get(get));
                }
            }
        }
        this.hashTable = newHash;
    }

    @Override
    public V put(K key, V value) {
        if ((1.0*this.size)/(1.0*this.hashTable.length)>load){
            this.resize();
        }
        int hash = Math.abs(key.hashCode() % this.hashTable.length);
        if (this.hashTable[hash] == null){
            this.hashTable[hash] = chain.get();
        }
        V val = ((IDictionary<K, V>) this.hashTable[hash]).get(key);
        if (val == null) {
            this.size++;
        }
        ((IDictionary<K, V>) this.hashTable[hash]).put(key, value);
        return val;
    }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    /**
     * @param value
     * @return true if the HashDictionary contains a key-value pair with
     * this value, and false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        return this.values().contains(value);
    }

    /**
     * @return number of key-value pairs in the HashDictionary
     */
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keys() {
        ICollection<K> keys = new LinkedDeque<>();
        for (Object dic : this.hashTable){
            if (dic != null) {
                for (K key : ((IDictionary<K, V>) dic).keySet()) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }

    @Override
    public ICollection<V> values() {
        ICollection<V> vals = new LinkedDeque<>();
        for (Object dic : this.hashTable){
            if (dic != null) {
                for (V value : ((IDictionary<K, V>) dic).values()) {
                    vals.add(value);
                }
            }
        }
        return vals;
    }

    /**
     * @return An iterator for all entries in the HashDictionary
     */
    @Override
    public Iterator<K> iterator() {
        return this.keys().iterator();
    }
}
