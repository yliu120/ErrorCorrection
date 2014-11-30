/**
 * 
 */
package edu.jhu.cs.cs439.project.exactcount;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;
import edu.jhu.cs.cs439.project.FastaParser;

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
	
	public static void main(String[] args) throws IOException {
		
		int K = 32;
		
		if ( args.length != 1 && args.length != 2 ) {
			System.err.println("Usage: ExactCount <Fasta file> <K>");
		} else if ( args.length == 2 ) {
			K = Integer.parseInt( args[1] );
		} else {
		}
		
		// For using the anoymous class
		final int kk = K;
		
		List<String> reads = FastaParser.SINGLETON.parseFasta(args[0]);
		
		SparkConf sparkConf = new SparkConf().setAppName("ExactCount");
		JavaSparkContext ctx = new JavaSparkContext( sparkConf );
		
		JavaRDD<String> readStrings = ctx.parallelize( reads );
		
		// Break each DNA sequence to K-mers
		JavaRDD<String> KMers = readStrings.flatMap( new FlatMapFunction<String, String>() {

			@Override
			public Iterable<String> call(String s) throws Exception {
				
				List<String> allKmerForOneRead = new ArrayList<>();
				for ( int i = 0; i <= s.length() - kk; i ++ ) {
					allKmerForOneRead.add(s.substring(i, i+kk-1));
				}
				
				return allKmerForOneRead;
				
			}
			
		});
		
		JavaPairRDD<String, Integer> ones = KMers.mapToPair( new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String s) throws Exception {
				return new Tuple2<String, Integer>(s, 1);
			}
			
		});
		
		JavaPairRDD<String, Integer> counts = ones.reduceByKey( new Function2<Integer, Integer, Integer>() {

			@Override
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
			
		});
		
		List<Tuple2<String, Integer>> output = counts.collect();
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("exact_count_kMers.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Tuple2<?,?> tuple : output) {
			fw.write(tuple._1() + " " + tuple._2() + "\n");
		}
		
		fw.flush();
		fw.close();
	}

}
