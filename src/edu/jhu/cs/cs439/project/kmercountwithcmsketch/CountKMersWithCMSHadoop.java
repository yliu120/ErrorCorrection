package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapOutputCollector.Context;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import edu.jhu.cs.cs439.project.RollingHashingKMers;

public class CountKMersWithCMSHadoop {

	public static class CMSMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		public static double delta;
		public static double epsilon;

		public final int width = (int) Math.ceil(Math.E / epsilon);
		public final int depth = (int) Math.ceil(Math.log(1 / delta));

		RollingHashingKMers hasher = new RollingHashingKMers(depth, width);

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String read = value.toString();
			char first = read.charAt(0);
			if (first != '@' && first != '2' && first != '+') {

				for (int i = 0; i <= read.length() - 15; i++) {

					String kmer = read.substring(i, i + 15);

					for (int j = 0; j < this.depth; j++) {
						int position_W = this.hasher.hashing(kmer, j);
						Text CMkey = new Text(j + "_" + position_W);
						IntWritable CMvalue = new IntWritable(1);
						context.write(CMkey, CMvalue);
					}

				}

			}

		}
	}

	public static class CMSReducer extends
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
				CountKMersWithCMSHadoop.class);
		Job countjob = Job.getInstance(jobconfiguration);
		// Set mapper output key&value format
		countjob.setMapOutputKeyClass(Text.class);
		countjob.setMapOutputValueClass(IntWritable.class);
		// Set reducer output keyt&value format
		countjob.setOutputKeyClass(IntWritable.class);
		countjob.setOutputValueClass(Text.class);
		// Set mapper and reducer class
		CMSMapper.delta = Double.parseDouble(args[2]);
		CMSMapper.epsilon = Double.parseDouble(args[3]);
		countjob.setMapperClass(CountKMersWithCMSHadoop.CMSMapper.class);
		countjob.setReducerClass(CountKMersWithCMSHadoop.CMSReducer.class);
		// Set input and output file
		FileInputFormat.addInputPath(countjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(countjob, new Path(args[1]));
		// submit the job already configured
		countjob.submit();
	}
}
