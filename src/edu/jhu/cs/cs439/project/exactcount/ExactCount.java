/**
 * 
 */
package edu.jhu.cs.cs439.project.exactcount;

/**
 * This class implements the exact count of all the k-mers.
 * The implementation will use spark-1.1.
 * The algorithm of MapReduce here is very similar to 
 * the classical word count algorithm.
 * Map: k-mer ---> < k-mer, 1 >
 * Reduce : <k-mer, list<1>> ---> <k-mer, count>
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since 0.0
 */
public class ExactCount {
	
	public static void main(String[] args) {
		
		if ( args.length != 1 ) {
			System.err.println("Usage: ExactCount <Fasta file>");
		}
		
	}

}
