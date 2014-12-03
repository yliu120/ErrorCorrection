/**
 * 
 */
package edu.jhu.cs.cs439.project;

import java.util.List;
import java.util.Map;

import edu.jhu.cs.cs439.project.projectinterface.HashingKMers;

/**
 * This class provide our hashing for KMers
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 0.0
 * @since 0.0
 */
public class RollingHashingKMers implements HashingKMers {

	private int depth;
	private int width;
	private List<Map<String, String>> functions;

	/**
	 * Constructor with two parameters
	 * 
	 * @param depth
	 *            the depth of count-min sketch
	 * @param width
	 *            the width of count-min sketch
	 */
	public RollingHashingKMers(int depth, int width) {
		super();
		this.depth = depth;
		this.width = width;
		this.functions = (new KMerHashers(this.depth, this.width))
				.getHashFunctions();
	}
	
	public RollingHashingKMers(int depth, int width, boolean test) {
		super();
		this.depth = depth;
		this.width = width;
		this.functions = (new KMerHashers(this.depth, this.width))
				.getFixedHashFunctions();
	}

	/**
	 * @see edu.jhu.cs.cs439.project.projectinterface.HashingKMers#hashing(java.lang.String)
	 */
	@Override
	public int hashing(String Kmer, int i) {

		if (i >= depth || i < 0) {
			throw new RuntimeException(
					"RollingHashingKMers (i) : out of range. "
							+ "Expected 0<=i<=" + depth + " but get " + i);
		}
		
		Map<String, String> function = this.functions.get(i);
		
		String hashCode = "";
		int lengthOfKmer = Kmer.length();
		
		for ( int j = 0; j < lengthOfKmer; j ++ ) {
			String c = "" + Kmer.charAt(j);
			hashCode += function.get( c );
		}
		
		return Integer.parseInt(hashCode, 2) % this.width;
	}

}
