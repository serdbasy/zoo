package program;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sorter<K, V extends Comparable<V>> implements Comparable<Sorter<K, V>> 
{
	// http://stackoverflow.com/questions/5013947/create-a-compareto-to-a-generic-class-that-implements-comparable#answer-17465448
	
	private K key;
    private V value;
    
    
    public K getKey()
    {
		return key;
	}

	public void setKey(K key)
	{
		this.key = key;
	}

	public V getValue()
	{
		return value;
	}

	public void setValue(V value)
	{
		this.value = value;
	}

	public Map<K, V> sortByValue(Map<K, V> unsortMap)
    {
        List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

        // Maintaining insertion order with the help of LinkedList
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (Entry<K, V> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

	@Override
	public int compareTo(Sorter<K, V> o)
	{
		// TODO Auto-generated method stub
		return this.getValue().compareTo(o.getValue());
	}

}
