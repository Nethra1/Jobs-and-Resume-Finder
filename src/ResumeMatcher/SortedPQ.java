package ResumeMatcher;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// unsorted priority queue
public class SortedPQ<K,V> 
	extends AbstractPriorityQueue<K,V> 
	{ 
	
	// primary collection
	private LinkedList<Item<K,V>> data; 

	// constructor calls the super
	public SortedPQ() { 
		super(); 
		data = new LinkedList<>();
	}
	public SortedPQ(Comparator<K> c) { 
		super(c); 
		data = new LinkedList<Item<K,V>>();
	}

	// of course, this is always useful to do right after the constructors
	public String toString() { 
		return data.toString();
	}

	public void clear() { 
		data.clear();
	}

	public void insert(K key, V value) { 
		checkKey(key);

		Item<K,V> newest = new Item<K,V>(key,value);

		if(isEmpty()) { 
			data.addFirst(newest);
		} else {
			
			Iterator<Item<K,V>> iter = data.descendingIterator();
			Item<K,V> walker = iter.next();
			// keep moving left while newest is less than walker
			int ix = data.size();
			while(walker!=null && compare(newest, walker)<0 ) {
				// advance walker left (left = next, descending iterator)
				try {
				    walker = iter.next();
					ix--;
				} catch(NoSuchElementException e) { 
					walker = null;
				}
			}
			// check what caused the stop
			if(walker==null) {  
				data.addFirst(newest);
			} else {
				data.add(ix,newest);
			}
		}
	}

	public V peekMin() {
		return data.get(0).getValue();
	}

	public V removeMin() {
		Item<K,V> it = data.remove(0);
		return it.getValue();
	}

	public int size() { 
		return data.size();
	}

	protected Iterable<Item<K,V>> items() { 
		return data;
	}

	protected class ItemIterator implements Iterator<K> {
		Iterator<Item<K,V>> it;
		public ItemIterator() { 
			it = items().iterator();
		}
		public boolean hasNext() { return it.hasNext(); }
		public K next() { return it.next().getKey(); }
		public void remove() { it.remove(); }
	}

	public Iterator<Item<K, V>> iterator(){
        return data.iterator();
    }



	public static void main(String[] args) { 

		System.out.println("***********************");
		System.out.println("**** small test *******");

		SortedPQ<Integer,Integer> q = new SortedPQ<Integer,Integer>();
		
		q.insert(new Integer(5 ), new Integer(500));
		q.insert(new Integer(2 ), new Integer(200));
		q.insert(new Integer(3 ), new Integer(300));
		q.insert(new Integer(1 ), new Integer(100));
		q.insert(new Integer(99), new Integer(900));
		q.insert(new Integer(8 ), new Integer(800));
		q.insert(new Integer(7 ), new Integer(700));

		System.out.println("Full queue:");
		System.out.println(q);
		
		Integer min;
		Integer onehundred = new Integer(100);

		min = q.removeMin();
		if(min.equals(onehundred)) {
			System.out.println("Removed expected minimum of 100.");
		} else {
			System.out.println("Removed unexpected minimum. Expected 100, got "+min);
		}

		min = q.removeMin();
		if(min.equals(2*onehundred)) {
			System.out.println("Removed expected minimum of 200.");
		} else {
			System.out.println("Removed unexpected minimum. Expected 200, got "+min);
		}

		min = q.removeMin();
		if(min.equals(3*onehundred)) {
			System.out.println("Removed expected minimum of 300.");
		} else {
			System.out.println("Removed unexpected minimum. Expected 300, got "+min);
		}

		System.out.println("After removing three minimum items:");
		System.out.println(q);

		System.out.println("Empty the remaining items with an iterator.");
		// iterator() returns a <key> iterator, not a <key,value> iterator
		Iterator<Item<Integer, Integer>> k = q.iterator();
		while(k.hasNext()) {
			k.next();
			k.remove();
		}
		System.out.println(q);
	}

}