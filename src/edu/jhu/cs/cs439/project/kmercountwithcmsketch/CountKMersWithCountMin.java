/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import edu.jhu.cs.cs439.project.FastaParser;

/**
 * This class implements the k-mer counting using the dynamics structure
 * count-min sketch. The algorithm is illustrate:
 * <p>
 * FlatMap: key x ---> <list of IJ> where IJ is the Ith hash function and J is
 * h_i(x), x is the K-mer. Mapper : key IJ ---> <IJ, 1> Reducer: <IJ, listof<1>>
 * ---> <IJ, count>
 * </p>
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 0.0
 * @since 0.0
 */
public class CountKMersWithCountMin {

	private double eps;
	private double delta;

	private int width;
	private int depth;

	private CountMinSketchParameters parameters;

	private String fileName;

	/**
	 * Default Constructor
	 * 
	 * @param eps
	 *            the epsilon parameter of the count-min sketch
	 * @param delta
	 *            the delta parameter of the count-min sketch
	 * @param fileName
	 *            the fasta file name
	 */
	public CountKMersWithCountMin(double eps, double delta, String fileName) {
		super();
		this.eps = eps;
		this.delta = delta;
		this.fileName = fileName;

		this.width = (int) Math.ceil(Math.E / this.eps);
		this.depth = (int) Math.ceil(Math.log(1 / this.delta));

		this.parameters = new CountMinSketchParameters(this.depth, this.width);
	}

	/**
	 * Get the epsilon of this model
	 * 
	 * @return the epsilon of this model
	 */
	public double getEps() {
		return eps;
	}

	/**
	 * Set the epsilon of this model
	 * 
	 * @param eps
	 *            the epsilon you would like to set
	 */
	public void setEps(double eps) {
		this.eps = eps;
	}

	/**
	 * Get the delta value of this model.
	 * 
	 * @return the delta value of this model.
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * Set the delta value of this model
	 * 
	 * @param delta
	 *            the delta you want to set
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}

	/**
	 * Get the parameters of this model
	 * 
	 * @return the Count-Min Sketch parameters
	 */
	public CountMinSketchParameters getParameters() {
		return parameters;
	}

	/**
	 * Provide a setter for parameters
	 * 
	 * @param parameters
	 *            the parameters you want to set.
	 */
	public void setParameters(CountMinSketchParameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * The fasta filename
	 * 
	 * @return the filename of this model.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set the fastafile name of the model.
	 * 
	 * @param fileName
	 *            The fasta filename.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static void main(String[] args) {

		int K = 15;
		String fileName = null ;
		double eps = 0.001;
		double delta = 0.001;

		if (args.length != 3 && args.length != 4) {
			System.err.println("Usage: ExactCount <Fasta file> <epsilon> <delta> Optional <K=15,15 default>");
		} 
		
		if (args.length == 3 || args.length == 4) {
			fileName = args[0];
			eps = Double.parseDouble( args[1] );
			delta = Double.parseDouble( args[2] );
		} 
		
		if ( args.length == 4 ) {
			K = Integer.parseInt(args[3]);
		}
		
		CountKMersWithCountMin count = new CountKMersWithCountMin(eps, delta, fileName);
		
		// Get all the reads from fasta file.
		List<String> reads = FastaParser.SINGLETON.parseFasta( fileName );
		
		// Setup spark
		SparkConf sparkConf = new SparkConf().setAppName("CountKMersWithCountMin");
		
		
		
		

	}

}
