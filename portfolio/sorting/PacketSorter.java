package sorting;

import java.util.Comparator;

/**
 * Class full of static sorting methods. Used to sort packets received from a
 * server containing image metadata.
 * 
 * TODO: Implement mergeSort() and selectionSort().
 * 
 * insertionSort is implemented for you as an example.
 * 
 * @author pattersp
 *
 */

public class PacketSorter {
    /**
     * Sorts the given array of packets in ascending order according to the
     * comparator using mergesort. You may create as many private helper
     * functions as you wish to implement this method.
     * 
     * A note about ascending order:
     * 
     * When the method is finished, it should be true that:
     * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
     * array.length.
     * 
     * @param array
     *            the packets to sort
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void mergeSort(Packet[] array, Comparator<Packet> comparator) {
    	Packet[] tmpArray = new Packet[ array.length ];
    	
    	mergeSort( array, tmpArray, 0, array.length - 1, comparator );
    }
    
    private static void mergeSort( Packet[] array, Packet[] tmpArray, int left, 
				int right, Comparator<Packet> comparator ) {
		if( left < right ) {
			int center = ( left + right ) / 2;
			mergeSort( array, tmpArray, left, center, comparator);
			mergeSort( array, tmpArray, center + 1, right, comparator);
			merge(array, tmpArray, left, center + 1, right, comparator);
		}
	
	}
	
	private static void merge( Packet[] array, Packet[] tmpArray, int leftPos,
								int rightPos, int rightEnd, Comparator<Packet> comparator ) {
		
			int leftEnd = rightPos -1;
			int tmpPos = leftPos;
			int numElements = rightEnd - leftPos + 1;
		
		while( leftPos <=  leftEnd && rightPos <= rightEnd) {
		
			if( comparator.compare(array[ leftPos ], array[ rightPos ] ) <= 0 ) {
				tmpArray[ tmpPos++ ] = array[ leftPos++ ];
			} else {
				tmpArray[tmpPos++ ] = array[ rightPos ++];
			}
		}
		
		while( leftPos <= leftEnd ) {
			tmpArray[ tmpPos++ ] = array[ leftPos++ ];
		}
		
		while( rightPos <= rightEnd ) {
			tmpArray[ tmpPos++ ] = array[ rightPos++ ];
		}
		
		for( int i = 0; i < numElements; i++, rightEnd--) {
			array[ rightEnd ] = tmpArray[ rightEnd ];
		}
		
	}

    /**
     * Sort the array of packets in ascending order using selection sort.
     * 
     * A note about ascending order:
     * 
     * When the method is finished, it should be true that:
     * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
     * array.length.
     * 
     * @param array
     *            the array of packets that will be sorted.
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void selectionSort(Packet[] array,
            Comparator<Packet> comparator) {
    	for( int i = 0; i < array.length; i++) {
    		int nextMin = nextMin( i, array, comparator);
    		swap( nextMin, i, array);
    	}
        
    }
    
    private static int nextMin( int curr, Packet[] array, Comparator<Packet> comparator) {
    	int min = curr;
    	for( int i = curr; i < array.length; i++ ) {
    		if( comparator.compare( array[ i ], array[ min ] ) < 0 ) {
    			min = i;
    		}
    	}
    	return min;
    }
    
    private static void swap(int nextMin, int curr, Packet[] array) {
    	Packet tmp = array[ curr ];
    	
    	array[curr] = array[ nextMin ];
    	array[ nextMin ] = tmp;
    	
    }
    
    /**
     * Sort the array of packets in ascending order using insertion sort.
     * 
     * A note about ascending order:
     * 
     * When the method is finished, it should be true that:
     * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
     * array.length.
     * 
     * @param array
     *            the array of packets that will be sorted.
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void insertionSort(Packet[] array,
            Comparator<Packet> comparator) {
        for (int outerIndex = 1; outerIndex < array.length; outerIndex++) {
            Packet currentPacket = array[outerIndex];
            int innerIndex = outerIndex - 1;
            while (innerIndex >= 0
                    && comparator.compare(currentPacket, array[innerIndex]) < 0) {
                array[innerIndex + 1] = array[innerIndex];
                innerIndex--;
            }
            array[innerIndex + 1] = currentPacket;
        }
    }
}
