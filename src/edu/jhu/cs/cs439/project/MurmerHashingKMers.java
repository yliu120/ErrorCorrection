package edu.jhu.cs.cs439.project;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import edu.jhu.cs.cs439.project.projectinterface.HashingKMers;

/**
 * 
 */

/**
 * This class implements a group of hashing functions based on Murmer hash.
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class MurmerHashingKMers implements HashingKMers {
	
	private int depth;
	private int width;
	private List<HashFunction> hashers;
	/**
	 * Default constructor using depth and width of CM
	 * @param depth
	 * @param width
	 */
	public MurmerHashingKMers(int depth, int width) {
		super();
		this.depth = depth;
		this.width = width;
		this.hashers = generateHashFunctions();
	}

	/**
	 * @see edu.jhu.cs.cs439.project.projectinterface.HashingKMers#hashing(java.lang.String, int)
	 */
	@Override
	public int hashing(String Kmer, int i) {
		
		HashFunction hasher = this.hashers.get(i);
		HashCode code = hasher.hashString(Kmer, Charsets.UTF_8);
		long hashValue = code.padToLong();
		return (int) (hashValue % this.width);
		
	}
	
	private List<HashFunction> generateHashFunctions() {
		
		List<HashFunction> hashFunctions = new ArrayList<>();
		
		for ( Integer seed : this.generateRandomSeeds() ) {
			
			HashFunction hasher = Hashing.murmur3_128(seed);
			hashFunctions.add(hasher);
			
		}
		
		return hashFunctions;
		
	}
	
	private Set<Integer> generateRandomSeeds() {
		
		Set<Integer> randomSeeds = new HashSet<>();
		Random random = new Random();
		
		while ( randomSeeds.size() != this.depth ) {
			randomSeeds.add( random.nextInt( this.width ) );
		}
		
		return randomSeeds;
		
	}

}
