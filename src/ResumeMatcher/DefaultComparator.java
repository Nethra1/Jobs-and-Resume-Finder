package ResumeMatcher;

import java.util.Comparator;

/** Define a default comparator for a default priority queue.
 *
 * This attempts to use natural ordering for the given keys.
 * This assumes the keys come from a comparable class.
 */
public class DefaultComparator<E> implements Comparator<E> {
	/** Compare two objects a and b using natural order. */
	public int compare(E a, E b) throws ClassCastException { 
		// The comparable cast is a way of ensuring (safely) 
		// that the Comparator interface has been extended
		// and that we have actually defined the compareTo method
		return ((Comparable<E>)a).compareTo(b);
	}
}