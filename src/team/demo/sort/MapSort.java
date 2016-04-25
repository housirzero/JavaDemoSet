package team.demo.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*******************************************************************************************
MapSort<String, Integer> mapSort = new MapSort<String, Integer>(new HashMap<String, Integer>()){

	@Override
	public int valueComp(Integer v1, Integer v2) {
		return v2 - v1;
	}
	
	@Override
	public int keyComp(String k1, String k2) {
		return k1.compareTo(k2);			
	}
};
			
List<MapClass<String, Integer>> sortList = mapSort.sortByValue();
*******************************************************************************************/
public abstract class MapSort<K, V> {
	
	public Map<K, V> map;
	
	public MapSort(Map<K, V> map) {
		this.map = map;
	}

	public List<MapClass<K, V>> sortByValue(){
		List<MapClass<K, V>> list = new ArrayList<MapClass<K, V>>();
		for(Entry<K, V> entry : map.entrySet())
			list.add(new MapClass<K, V>(entry.getKey(), entry.getValue()));
		Collections.sort(list, new SortByValue());
		return list;
	}

	public List<MapClass<K, V>> sortByKey(){
		List<MapClass<K, V>> list = new ArrayList<MapClass<K, V>>();
		for(Entry<K, V> entry : map.entrySet())
			list.add(new MapClass<K, V>(entry.getKey(), entry.getValue()));
		Collections.sort(list, new SortByKey());
		return list;
	}

	public abstract int valueComp(V v1, V v2);
	public abstract int keyComp(K k1, K k2);

	class SortByValue implements Comparator<MapClass<K, V>>
	{
		@Override
		public int compare(MapClass<K, V> o1, MapClass<K, V> o2) {
			return valueComp(o1.v, o2.v);
		}
	}
	
	class SortByKey implements Comparator<MapClass<K, V>>
	{
		@Override
		public int compare(MapClass<K, V> o1, MapClass<K, V> o2) {
			return keyComp(o1.k, o2.k);
		}
	}
}

