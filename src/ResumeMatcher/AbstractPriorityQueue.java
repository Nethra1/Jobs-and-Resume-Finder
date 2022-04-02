package ResumeMatcher;

import java.util.Comparator;

/** Priority Queue ADT.
 *
 * Model elements and their priorities as a key-value composite Entry.
 *
 * The ADT is where we define any constructors or methods
 * that are possible to define without knowing the concrete
 * implementation of our priority queue.
 */
public abstract class AbstractPriorityQueue<K,V> 
			implements PQ<K,V> {

	public static class Item<K,V> {
		private K k;
		private V v;
		public Item(K key, V value) { 
			this.k = key; this.v = value;
		}

		/** Return the key associated with this element. */
		public K getKey() { return k; }

		/** Return the value associated with this element. */
		public V getValue() { return v; }

		/** Set the key associated with this element. */
		protected void setKey(K k) { this.k = k; }

		/** Set the value associated with this element. */
		protected void setValue(V v) { this.v = v; }

		public String toString() { return "("+v+")"; }
	}

	// Key comparator
	private Comparator<K> keyCompare;

	/** Create an empty priority queue that will use the given comparator to sort the item keys. */
	protected AbstractPriorityQueue(Comparator<K> c) { keyCompare = c; }

	/** Create an empty priority queue with a default comparator */
	protected AbstractPriorityQueue() { 
		// this() call must be first thing to happen in constructor
		this( new DefaultComparator<K>() );
	}

	/** Define a way to compare two Items.
	 * This is a more general way than defiing a compareTo method.
	 * By using a Comparator object and defining the compare(a,b) method,
	 * we can create multiple priority queues, each using their own
	 * method for sorting keys.
	 *
	 * Throws an unchecked WeirdKey.
	 * */
	protected int compare(Item<K,V> a, Item<K,V> b) { 
		// Compare two Items 
		// using keyComparator
		// and passing their keys.
		// Don't bother with the details.
		return keyCompare.compare(a.getKey(), b.getKey());
	}

	/** Determine whether a key can be compared to itself using our comparator object. */
	protected boolean checkKey(K key) {
		try{ 
			return keyCompare.compare(key,key)==0;
		} catch (ClassCastException e) {
			throw new WeirdKey();
		}
	}

	/** Test whether priority queue is empty - assumes size() method is implemented. */
	public boolean isEmpty() {
		return (size()==0);
	}

}