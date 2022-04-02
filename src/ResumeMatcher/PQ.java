package ResumeMatcher;

import java.util.Iterator;

/** Illegal stuff exception - hide the drugs! */
class Illegal extends IllegalArgumentException {}

/** Empty exception. */
class Empty extends IndexOutOfBoundsException {}

/** Define a weird key exception.
 * This gets raised when a key cannot be compared to itself. 
 * This extends IllegalArgumentException which means it is unchecked. */
class WeirdKey extends IllegalArgumentException {};


/** Priority Queue interface.
 *
 * This defines the two simplest methods a priority queue can get away with.
 *
 * This is essentially a barebones list of methods. 
 *
 * Note that Item<K,V> is not defined here, it is defined in your concrete implementation.
 */
public interface PQ<K,V> {

	/** Returns true if the priority queue was changed. */
	public void insert(K k, V v);

	/** Remove and return the minimum element in this queue. */
	public V removeMin() throws Empty;

	/** Return, but do not remove, the minimum element in this queue. */
	public V peekMin() throws Empty;

	/** Returns a key-based iterator. */
	public Iterator<ResumeMatcher.AbstractPriorityQueue.Item<K,V>> iterator();

	/** Clear all elements from this container. */
	public void clear();

	/** Returns the number of elements in this queue. */
	public int size();

	/** Returns true if there are no elements in this queue. */
	public boolean isEmpty();

	/** Returns a string representation of this priority queue. */
	public String toString();
}