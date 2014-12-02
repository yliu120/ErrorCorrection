/**
 * 
 */
package edu.jhu.cs.cs439.project.exactcount;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

/**
 * This class provides a streaming version of exact count.
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class ExactCountStreaming {

	public static void main(String[] args) {

		int K = 15;

		if (args.length != 1 && args.length != 2) {
			System.err.println("Usage: ExactCountStreaming <Fasta Directory> [Optional] <K>");
		} else if (args.length == 2) {
			K = Integer.parseInt(args[1]);
		} else {
		}

		// For using the anoymous class
		final int kk = K;

		SparkConf sparkConf = new SparkConf()
				.setAppName("ExactCountStreamingVersion");
		JavaStreamingContext jsc = new JavaStreamingContext(sparkConf,
				new Duration(2000));

		JavaDStream<String> lines = jsc.textFileStream(args[0]);
		JavaDStream<String> words = lines
				.flatMap(new FlatMapFunction<String, String>() {

					@Override
					public Iterable<String> call(String x) throws Exception {

						List<String> kmers = new ArrayList<>();

						if (x.charAt(0) == '@' || x.charAt(0) == '+') {
							return kmers;
						}

						for (int i = 0; i < 8; i++) {
							char q = x.charAt(i);
							if (q != 'A' && q != 'C' && q != 'G' && q != 'T') {
								return kmers;
							}
						}

						for (int i = 0; i <= x.length() - kk; i++) {
							kmers.add(x.substring(i, i + kk - 1));
						}

						return kmers;

					}

				});

		JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
				new PairFunction<String, String, Integer>() {
					@Override
					public Tuple2<String, Integer> call(String s) {
						return new Tuple2<String, Integer>(s, 1);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2;
			}
		});
		
		wordCounts.print();
		System.out.println("What the fuck!");

		jsc.start();
		jsc.awaitTermination();

	}
}
