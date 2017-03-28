import java.util.List;

public class ThreeHeap implements PriorityQueue {

	public ThreeHeap() {
		this.doClear();
	}

	/**
	 * Returns true if priority queue has no elements
	 *
	 * @return true if the priority queue has no elements
	 */
	public boolean isEmpty() {
		return !(this.size() > 0);
	}
	
	/**
	 * Returns the number of elements in this priority queue.
	 *
	 * @return the number of elements in this priority queue.
	 */
	public int size() {
		return this.currentSize;
	}

	/**
	 * Returns the minimum element in the priority queue
	 *
	 * @throw EmptyHeapException if empty
	 * @return the minimum element
	 */
	public double findMin() {
		if(this.isEmpty()) {
			throw new EmptyHeapException();
		}
		
		return array[1];
	}

	/**
	 * Inserts a new element into the priority queue.
	 * Duplicate values ARE allowed.
	 *
	 * @param x element to be inserted into the priority queue.
	 */
	public void insert(double x) {
		if( this.currentSize == this.array.length - 1 ) {
			enlargeArray( this.array.length * 2 + 1 );
		}
		
		int hole = ++this.currentSize;
		for( array[0] = x; x < this.array[ (hole + 1) / 3 ]; hole = (hole + 1) / 3 ) {
			this.array[ hole ] = this.array[ (hole + 1) / 3 ];
		}
		this.array[ hole ] = x;
	}

	/**
	 * Removes and returns the minimum element from the priority queue.
	 *
	 * @throws EmptyHeapException if empty
	 * @return the minimum element 
	 */
	public double deleteMin() {
		if( this.isEmpty() ) {
			throw new EmptyHeapException();
		}
		
		double minItem = this.findMin();
		this.array[ 1 ] = this.array[ this.currentSize-- ];
		percolateDown( 1 );
		
		return minItem;
	}

	/**
	 * Resets the priority queue to appear as not containing any of the
	 * previous elements, then inserts each element from the given List
	 * into the priority queue.
	 * Duplicate values ARE allowed.
	 *
	 * @param list elements to be inserted into the priority queue.
	 */
	public void buildQueue(List<Double> list) {
		
		this.currentSize = list.size();
		this.array = new double[ currentSize * 2 ];
		
		int i = 1;
		for( double item : list) {
			this.array[i++] = item;
		}
		sortHeap();
	}

	/**
	 * Resets the priority queue to appear as not containing
	 * any elements.
	 */
	public void makeEmpty() {
		this.doClear();
	}
	
	private static final int DEFAULT_CAPACITY = 18;
	
	private int currentSize;
	private double [] array;
	
	private void percolateDown( int hole ) {
		
		int child;
		double tmp = this.array[ hole ];
		
		for( ; hole * 3 - 1 <= this.currentSize; hole = child) {
			child = hole * 3 - 1;
			if( child != currentSize && ( this.array[ child + 1 ] < this.array[ child ]  
				|| this.array[ child + 2 ] < this.array[ child ] ) ) {
				child++;
				if(child != currentSize && this.array[ child + 1 ] < this.array[ child ] ) {
					child++;
				}
			}
			
			if( this.array[ child ] < tmp ) {
				this.array[ hole ] = this.array[ child ];
			} else {
				break;
			}
		}	
		array[ hole ] = tmp;	
	}
	
	private void doClear() {
		this.array = new double[DEFAULT_CAPACITY];
		this.currentSize = 0;
		this.array[0] = currentSize;
	}
	
	private void enlargeArray( int newSize ) {
		double [] old = this.array;
		this.array = new double[newSize];
		for( int i = 0; i <= this.size();  i++) {
			this.array[i] = old[i];
		}
	}
	
	private void sortHeap() {
		for( int i = (currentSize + 1) / 3 ; i > 0; i--) {
			percolateDown(i);
		}
	}
}
