/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

/**
 * This class encapsulates all the parameters of the countmin sketch
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since   0.0
 */
public class CountMinSketchParameters {
	
	// The count-min-sketch
	private int[][] dw;

	public CountMinSketchParameters ( int d, int w ) {
		this.dw = new int[d][w];
	}
	
}
