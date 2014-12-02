/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import edu.jhu.cs.cs439.project.RollingHashingKMers;
import edu.jhu.cs.cs439.project.projectinterface.HashingKMers;

/**
 * This class encapsulates all the parameters and methods of the countmin sketch
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since   0.0
 */
public class CountMinSketchParameters {
	
	// The count-min-sketch
	private int d;
	private int w;
	private int[][] dw;
	private HashingKMers hashers;

	/**
	 * Default Constructor.
	 * We are not using other hashers,
	 * however, for future purposes, we can use other trained hashers
	 * Here, we get use the rolling hasher that we have already.
	 * Note that if we have other hashers, we can use a constructor downwards.
	 * @param d depth
	 * @param w width
	 */
	public CountMinSketchParameters ( int d, int w ) {
		this.d  = d;
		this.w  = w;
		this.dw = new int[d][w];
		
		// We are not using other hashers,
		// however, for future purposes, we can use other trained hashers
		// Here, we get use the rolling hasher that we have already.
		// Note that if we have other hashers, we can use a constructor downwards.
		this.hashers = new RollingHashingKMers(d, w);
	}
	
	/**
	 * Using this constructor to give specified hashers.
	 * @param d depth
	 * @param w width
	 * @param hashers specified hashers
	 */
	public CountMinSketchParameters ( int d, int w, HashingKMers hashers ) {
		this.dw = new int[d][w];
		this.hashers = hashers;
	}
	
	/**
	 * Using this function to set sketch values
	 * @param d the row number
	 * @param w the column number
	 * @param number the number you want to set.
	 */
	public void setSketch( int d, int w, int number ) {
		this.dw[d][w] = number; 
	}
	
	/**
	 * Print sketch to console
	 */
	public void printSketch() {
		
		System.out.println("---------------------------------------------");
		System.out.println("Count-Min Sketch: format: row column value");
		System.out.println("Note that row and column are starting from 1.");
		System.out.println("---------------------------------------------");
		for ( int i = 0; i < d; i ++ ) {
			for ( int j = 0; j < w; j ++ ) {
				System.out.println( i + " " + j + " " + this.dw[i][j]);
			}
		}
		
	}
	
	//TODO
	public void printSketchToFile( String fileName ) {
		
	}
	
	/**
	 * Get the sketch as a two-dimensional int array.
	 * with row (depth) and column (width)
	 * Value starts with indices 1j and i1.
	 * @return the int array
	 */
	public int[][] getSketch() {
		return this.dw;
	}
	
	public HashingKMers getHashers() {
		return this.hashers;
	}
	
}
