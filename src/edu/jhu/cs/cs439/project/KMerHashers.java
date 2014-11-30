package edu.jhu.cs.cs439.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class implements hash function library that can used for Count-min sketch.
 * The library gives a maximum 24 hash-functions for the sake of 
 * d = ceil( log( 1/delta ) ). We doesn't really need this much.
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class KMerHashers implements Hashers {
	
	// This field will determine how many hashers
	private int depth;
	// This field will determine the hashcode for KMers.
	private int K;
	
	// Hash function libraries
	private List<Map<String, String>> hashLibraries;

	/**
	 * This default constructor takes two parameters to generate hashers
	 * @param depth the number of hasher needed
	 * @param k the "K"-mer's K
	 */
	public KMerHashers(int depth, int k) {
		
		super();
		this.depth = depth;
		K = k;
		
		this.hashLibraries = new ArrayList<>();
		
		this.generateHashLibraries();
		
	}

	
	/**
	 * @see Hashers#getHashFunctions()
	 * @return Hash Functions as list of map
	 */
	@Override
	public List<Map<String, String>> getHashFunctions() {
		
		List<Map<String, String>> hashFunctions = new ArrayList<>();
		
		for ( Integer i : this.generateDRandomNumber() ) {
			hashFunctions.add( this.hashLibraries.get(i) );
		}
		
		return hashFunctions;
		
	}
	
	private void generateHashLibraries() {
		
		Permutations permut = new Permutations();
		permut.perm2("ACGT");
		List<String> acgt = permut.getAcgt();
		
		for ( int i = 0; i < acgt.size() ; i ++ ) {
			
			String hashAcgt = acgt.get(i);
			
			Map<String, String> hashFuncAcgt = new HashMap<>();
			hashFuncAcgt.put(hashAcgt.substring(0, 1), "00");
			hashFuncAcgt.put(hashAcgt.substring(1, 2), "01");
			hashFuncAcgt.put(hashAcgt.substring(2, 3), "10");
			hashFuncAcgt.put(hashAcgt.substring(3, 4), "11");
			
			this.hashLibraries.add( hashFuncAcgt );
			
		}
		
	}
	
	private Set<Integer> generateDRandomNumber() {
		
		Set<Integer> dRandom = new HashSet<>();
		Random random = new Random();
		
		while( true ) {
			if ( dRandom.size() < this.depth ) {
				
				// See Permutations to know why we have 24 here.
				dRandom.add( random.nextInt(24) );
				
			} else {
				break;
			}
		}
		
		return dRandom;
	}
	

}
