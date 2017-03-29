import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* 
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	private static final int DEFAULT_TABLE_SIZE = 101;
	private int load;
	private int currentHash;
	
	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add( WordInfo wi ) {
			if( this.chain.contains( wi ) ) {
				return false;
			}
			return this.chain.add(wi);
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			return this.chain.remove(wi);
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
		
		private WordInfo find( String word) {
			for( WordInfo obj : this.chain) {
				if( obj.getWord().equals(word)) {
					return obj;
				}
			}
			return null;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		this.size = DEFAULT_TABLE_SIZE;
		this.table = new WordInfoSeparateChain[ DEFAULT_TABLE_SIZE ];
		this.load = 0;
	}
	
	
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		if(this.load == this.table.length/2) {
			resize();
		}
		if( !this.containsChain( word ) ) {
			WordInfoSeparateChain newChain = new WordInfoSeparateChain();
			this.table[this.index( word ) ] = newChain;
			this.load++;
			return this.table[this.index( word ) ].add( new WordInfo( word ) );
		} else if( !this.containsWord( word ) ) {
			this.load++;
			return this.table[this.index( word ) ].add( new WordInfo( word ) );
		}
		return false;
	}
	
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		if( this.containsChain( word ) && this.containsWord( word ) ) {
		    return this.table[this.index( word ) ].find( word ).addAssociation( association );
		}
		return false;
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		if( this.containsChain( word ) && this.containsWord( word ) ) {
		    return this.table[this.index( word ) ].remove( this.table[this.index( word ) ].find( word ) );
		}
		return false;
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		if( this.containsChain( word ) && this.containsWord( word ) ) {
		    return this.table[this.index( word ) ].find( word ).getAssociations();
		}
		return null;
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	private int index( String word) {
		currentHash = Math.abs(word.hashCode() * 31) % this.size;
		return Math.abs(word.hashCode() * 31) % this.size;
	}	
	
	private boolean containsChain( String word) {
		if( this.table[this.index( word ) ] == null ) {
			return false;
		} 
		return true;
	}
	
	private boolean containsWord( String word) {
		if( this.table[this.index( word ) ].find( word ) == null) {
			return false;
		} 
		return true;
	}
	
	private void resize() {
		this.size = this.size * 2 + 31;
		this.load = 0;
		
		WordInfoSeparateChain [] old = this.table;
		this.table = new WordInfoSeparateChain[this.size];
		for( int i = 0; i < old.length; i++ ) {
			if( old[ i ] != null ) {
				for( WordInfo wi : old[ i ].chain) {
					WordInfoSeparateChain newChain = new WordInfoSeparateChain();
					this.table[this.index( wi.getWord() ) ] = newChain;
					boolean grabBool =  this.table[this.index( wi.getWord() ) ].add( wi ); 
				}
			}	
		}
		
	}
}
