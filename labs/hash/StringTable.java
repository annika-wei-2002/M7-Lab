package hash;

import java.util.LinkedList;
import java.util.ListIterator;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
    
    private LinkedList<Record>[] buckets;
    private int nBuckets;

    //
    // number of records currently stored in table --
    // must be maintained by all operations
    //
    public int size; 
    
    
    //
    // Create an empty table with nBuckets buckets
    //
    @SuppressWarnings("unchecked")
	public StringTable(int nBuckets)
    {
    	this.nBuckets = nBuckets;
    	buckets = new LinkedList[nBuckets];
	
    	for (int i = 0; i < nBuckets; i++) {
    		buckets[i] = new LinkedList<Record>();
    	}

    }
    
    
    /**
     * insert - inserts a record to the StringTable
     *
     * @param r
     * @return true if the insertion was successful, or false if a
     *         record with the same key is already present in the table.
     */
    public boolean insert(Record r) 
    {  
    	String key = r.key;
    	
    	int hashCode = stringToHashCode(key);
    	
    	int index = toIndex(hashCode);
    	
    	LinkedList<Record> rBucket = buckets[index];
    	
    	if (rBucket.isEmpty() == true) {
    		rBucket.add(r);
    		size ++;
    		return true;
    	}
    	
    	ListIterator<Record> iterateThroughList = rBucket.listIterator();
    	
    	while (iterateThroughList.hasNext()) {
    		
    		Record recordInBucket = iterateThroughList.next();
    		
    		if (key.equals(recordInBucket.key)) {
    			return false;
    		}
    		
    	}
	
    	rBucket.addLast(r);
    	size++;
    	return true;
    }
    
    
    /**
     * find - finds the record with a key matching the input.
     *
     * @param key
     * @return the record matching this key, or null if it does not exist.
     */
    public Record find(String key) 
    {
    	int hashCode = stringToHashCode(key);
    	
    	int index = toIndex(hashCode);
    	
    	LinkedList<Record> rBucket = buckets[index];
    	
    	if (rBucket.isEmpty() == true) {
    		return null;
    	}
	
    	ListIterator<Record> iterateThroughList = rBucket.listIterator();
    	
    	while (iterateThroughList.hasNext()) {
    		
    		Record recordInBucket = iterateThroughList.next();
    		
    		if (key.equals(recordInBucket.key)) {
    			return recordInBucket;
    		}
    	
    	}
    	
    	return null;
    }
    
    
    /**
     * remove - finds a record in the StringTable with the given key
     * and removes the record if it exists.
     *
     * @param key
     */
    public void remove(String key) 
    {
    	
    	int hashCode = stringToHashCode(key);
    	
    	int index = toIndex(hashCode);
    	
    	LinkedList<Record> rBucket = buckets[index];
    	
    	if (rBucket.isEmpty() == false) {
    		
    		ListIterator<Record> iterateThroughList = rBucket.listIterator();
        	
        	while (iterateThroughList.hasNext()) {
        		
        		Record recordInBucket = iterateThroughList.next();
        		
        		if (key.equals(recordInBucket.key)) {
        			
        			rBucket.remove(recordInBucket);
        			
        			size--;
        			
        			break;
        			
        		}
        		
        		
        	}
    		
    	}
    	
    	
    }
    

    /**
     * toIndex - convert a string's hashcode to a table index
     *
     * As part of your hashing computation, you need to convert the
     * hashcode of a key string (computed using the provided function
     * stringToHashCode) to a bucket index in the hash table.
     *
     * You should use a multiplicative hashing strategy to convert
     * hashcodes to indices.  You can use the floating-point computation.
     */
    private int toIndex(int hashcode)
    {
    	
    	// use the Golden Ratio: (sqrt(5)-1)/2
    	double phi = (Math.sqrt(5)-1)/2;
    	
    	int index = Math.abs((int)(((hashcode * phi) % 1.0) * nBuckets));
	
    	return index;
    }
    
    
    /**
     * stringToHashCode
     * Converts a String key into an integer that serves as input to
     * hash functions.  This mapping is based on the idea of integer
     * polynomial hashing, where we do multiplies for successive
     * characters of the key (adding in the position to distinguish
     * permutations of the key from each other).
     *
     * @param string to hash
     * @returns hashcode
     */
    int stringToHashCode(String key)
    {
    	int A = 1952786893;
	
    	int v = A;
    	for (int j = 0; j < key.length(); j++)
	    {
    		char c = key.charAt(j);
    		v = A * (v + (int) c + j) >> 16;
	    }
	
    	return v;
    }

    /**
     * Use this function to print out your table for debugging
     * purposes.
     */
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();
	
    	for(int i = 0; i < nBuckets; i++) 
	    {
    		sb.append(i+ "  ");
    		if (buckets[i] == null) 
		    {
    			sb.append("\n");
    			continue;
		    }
    		for (Record r : buckets[i]) 
		    {
    			sb.append(r.key + "  ");
		    }
    		sb.append("\n");
	    }
    	return sb.toString();
    }
}
