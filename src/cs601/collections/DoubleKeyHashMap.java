package cs601.collections;

import java.util.*;

/**
 * Created by Ivan on 2014/8/23.
 */
public class DoubleKeyHashMap<K1,K2,V> implements DoubleKeyMap<K1,K2,V>
{
    class Entry<K1,K2,V>
    {
        K1 key1; K2 key2; V value;

        Entry(K1 key1, K2 key2, V value)
        {
            this.key1 = key1;
            this.key2 = key2;
            this.value = value;
            //int hash=Math.abs(key1.hashCode()*37+key2.hashCode());
        }

        public void setK1(K1 key1) { this.key1=key1;}
        public void setK2(K2 key2) { this.key2=key2;}
        public V setValue(V newValue)
        {
            value = newValue;
            return newValue;
        }

        public K1 getK1(){return key1;}
        public K2 getK2(){return key2;}
        public V getV() {return value;}
	}

    private Entry[] hashMap;
    private int count;

	public DoubleKeyHashMap()
	{
		this(1000); // reuse of the other constructor
	}

	public DoubleKeyHashMap(int i) {
        // these are setting local variables, which has no effect(modified)
        hashMap = new Entry[i];
    }

    public int Hash(K1 key1,K2 key2)
    {
        int hash;
        hash=Math.abs(key1.hashCode()*37+key2.hashCode());
        return hash;
    }

    public int IndexFor(int hash)
    {
        int mod;
        int index;
        mod=hashMap.length-1;
        index=hash&mod;
        return  index;
    }


	@Override
    /** Add (key1,key2,value) to dictionary, overwriting previous
     * value if any. key1, key2, and value must all be non-null.
     * If an boolean key1;y is null, throw IllegalArgumentException.
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *
     */
    public V put(K1 key1, K2 key2, V value)
    {
        if (key1==null||key2==null||value==null)
            throw new IllegalArgumentException();
        else {
            if (key1 != null && key2 != null) {
                //int hash = Math.abs(key1.hashCode() * 37 + key2.hashCode()); // this hash function should be in a separate function
                int hash=Hash(key1, key2);
                int i =IndexFor(hash);
                if (hashMap[i]==null)
                {
					// you can easily re-factor this code to be similar
                   hashMap[i]=new Entry(key1,key2,value);
                   count++;
                }
                else if (hashMap[i]!=null)
                {

                    V oldValue=(V)hashMap[i].getV();
                    V tmp=oldValue;
                    hashMap[i].setValue(value);
                    return tmp;
                }
            }
        }
        return null;
    }

    @Override
    /** Return the value associated with (key1, key2). Return null if
     * no value exists for those keys. key1, key2 must be non-null.
     * If any is null, throw IllegalArgumentException. The value
     * should be just the value added by the user via put(), and
     * should not contain any of your internal bookkeeping
     * data/records.
     */
    public V get(K1 key1, K2 key2)
    {
        if (key1==null||key2==null) {
            throw new IllegalArgumentException();
        }
        else {
            int hash=Hash(key1, key2);
            int i =IndexFor(hash);
            Entry en = hashMap[i];
            if ((key1.equals(en.key1)) && (key2.equals(en.key2)))
                return (V)en.value;
        }

        return null;
    }

    @Override
    /** Remove a value if present. Return previous value if any.
     * Do nothing if not present.
     */
    public V remove(K1 key1, K2 key2)
    {
        int hash=Hash(key1, key2);
        int i =IndexFor(hash);
        V va=(V)hashMap[i].getV();
        if (va!=null) {
            hashMap[i]=null;
            return va;
        }
        return null;
    }

    @Override
    /** Return true if there is a value associated with the 2 keys
     * else return false.
     * key1, key2 must be non-null. If either is null, return false.
     */
    public boolean containsKey(K1 key1, K2 key2)
    {
        if (key1==null||key2==null)
            return false;
        for (int j = 0; j < hashMap.length; j++) {
            Entry e = hashMap[j];
            if (key1.equals(e.getK1())  && key2.equals(e.getK2()))
                return true;
        }
        return false;
    }

    @Override
    /** Return list of a values in the map/dictionary. Return an
     * empty list if there are no values. The values should be just
     * the values added by the user via put(), and should not contain
     * any of your internal bookkeeping data/records.
     */
    public List<V> values()
    {
        List<V> vList=new ArrayList<V>();
        for (int i=0;i<hashMap.length;i++)
            vList.add((V)hashMap[i].getV());
        return vList;
    }

    @Override
    /** Return how many elements there are in the dictionary. */
    public int size(){
        return count;
    }

    @Override
    /** Reset the dictionary so there are no elements. */
    public void clear() {
        for(int i=0;i<hashMap.length;i++) {
            hashMap[i]=null;
        }
        count=0;
    }

}
