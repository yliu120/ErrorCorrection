/**
 * 
 */
package edu.jhu.cs.cs439.project.projectinterface;

/**
 * Implementing this interface to provide the hash value for Kmer
 * hashfunction: x(String) ---> a value of [1,...,w](int)
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since 0.0
 */
public interface HashingKMers {
	
	/**
	 * call this function to hash Kmer input.
	 * @param Kmer the Kmer you want to hash
	 * @param i hash using the ith hasher
	 * @return the hashCode
	 */
	public int hashing( String Kmer, int i );

}
