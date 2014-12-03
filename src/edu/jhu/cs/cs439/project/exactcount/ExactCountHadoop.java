package edu.jhu.cs.cs439.project.exactcount;

/**
 * The hadoop implementation of exact count
 * @author Yunlong Liu
 * @author Yijie   Li
 */

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ExactCountHadoop {

	public static class ECMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String read = value.toString();
			char first = read.charAt(0);
			if (first != '@' && first != '2' && first != '+') {

				for (int i = 0; i <= read.length() - 15; i++) {

					String kmer = read.substring(i, i + 15);

						Text ECkey = new Text( kmer );
						IntWritable ECvalue = new IntWritable(1);
						context.write( ECkey, ECvalue );

				}

			}

		}
	}

	public static class ECReducer extends
			Reducer<Text, IntWritable, IntWritable, Text> {

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(new IntWritable(sum), key);
		}
	}

	public static void main(String[] args) throws Exception {

		// JobConfiguration
		JobConf jobconfiguration = new JobConf(new Configuration(),
				ExactCountHadoop.class);
		Job countjob = Job.getInstance(jobconfiguration);
		// Set mapper output key&value format
		countjob.setMapOutputKeyClass(Text.class);
		countjob.setMapOutputValueClass(IntWritable.class);
		// Set reducer output keyt&value format
		countjob.setOutputKeyClass(IntWritable.class);
		countjob.setOutputValueClass(Text.class);
		// Set mapper and reducer class
		countjob.setMapperClass( ExactCountHadoop.ECMapper.class );
		countjob.setReducerClass( ExactCountHadoop.ECReducer.class );
		// Set input and output file
		FileInputFormat.addInputPath(countjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(countjob, new Path(args[1]));
		// submit the job already configured
		countjob.submit();
	}
}
