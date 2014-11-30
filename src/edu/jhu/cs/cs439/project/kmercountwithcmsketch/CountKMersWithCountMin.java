/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

/**
 * This class implements the k-mer counting using the
 * dynamics structure count-min sketch.
 * The algorithm is illustrate: 
 * <p> 
 * FlatMap: key x ---> <list of IJ>
 * where IJ is the Ith hash function and J is h_i(x), x is the K-mer.
 * Mapper : key IJ ---> <IJ, 1>
 * Reducer: <IJ, listof<1>> ---> <IJ, count>
 * </p>
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since 0.0
 */
public class CountKMersWithCountMin {
	
	private double eps;
	private double delta;
	
	private double width;
	private double depth;
	
	private String fileName;
	
	private final long BIGPRIME = 9223372036854775783L;

	/**
	 * Default Constructor
	 * @param eps the epsilon parameter of the count-min sketch
	 * @param delta the delta parameter of the count-min sketch
	 * @param fileName the fasta file name
	 */
	public CountKMersWithCountMin(double eps, double delta, String fileName) {
		super();
		this.eps = eps;
		this.delta = delta;
		this.fileName = fileName;
		
		this.width = Math.ceil( Math.E / this.eps );
		this.depth = Math.ceil( Math.log( 1 / this.delta ));
	}
	
	public static void main(String[] args) {
		
		
		
	}
	
	private int hashCode(String X, int i) {
		
		return 0;
		
	}

}
