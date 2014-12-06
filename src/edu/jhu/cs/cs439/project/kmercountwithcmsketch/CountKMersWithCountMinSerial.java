/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import edu.jhu.cs.cs439.project.MurmerHashingKMers;

/**
 * This class implements the serial algorithm: Counting K-mers using count-min
 * sketch
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class CountKMersWithCountMinSerial {

	public static void main(String[] args) throws IOException {

		// TODO: Refactoring using extracting method
		if (args.length != 4 && args.length != 5) {
			System.out
					.println("Usage: CountKMersWithCountMinSerial <fq file> <delta> <epsilon> <output> [optional]");
			System.out.println("     [optional] <hashfunctionset>");
			System.out
					.println("     <hashfunctionset> value: murmer - KMer will be hashed based on Murmer_32 algorithm.");
			System.out
					.println("     <hashfunctionset> value: rolling - KMer will be hashed based on Rolling algorithm");
			System.out
					.println("Please note that we only accept murmer and rolling as value of <hashfunctionset>");
			return;
		}

		final String fileName = args[0];
		final String output = args[3];
		String hashset = "rolling";

		// TODO: Refactoring using the factory design pattern
		if (args.length == 5) {

			hashset = args[4];

		}

		final double delta = Double.parseDouble(args[1]);
		final double epsilon = Double.parseDouble(args[2]);

		final int width = (int) Math.ceil(Math.E / epsilon);
		final int depth = (int) Math.ceil(Math.log(1 / delta));
		final CountMinSketchParameters sketch;

		// TODO: Refactoring using the factory design pattern
		if (hashset == "murmer") {
			sketch = new CountMinSketchParameters(depth, width,
					new MurmerHashingKMers(depth, width));
		} else {
			sketch = new CountMinSketchParameters(depth, width);
		}

		File file = new File(fileName);
		Scanner scanner = null;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Parsing fq file - file not found!");
		}

		final long startTime = System.currentTimeMillis();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.charAt(0) == '@') {
				line = scanner.nextLine();

				for (int j = 0; j < line.length() - 15; j++) {

					final String kmer = line.substring(j, j + 15);

					for (int t = 0; t < depth; t++) {
						int ww = sketch.getHashers().hashing(kmer, t);
						int update = sketch.getSketch()[t][ww] + 1;
						sketch.setSketch(t, ww, update);
					}

				}

			}
		}

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();
		final int mb = 1024 * 1024;

		System.out.println("##### Heap utilization statistics [MB] #####");

		// Print used memory
		System.out.println("Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		System.out.println("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);

		scanner.close();

		final long endTime = System.currentTimeMillis();
		System.out.println("Time using: "
				+ (float) (endTime - startTime) / 1000.0 + " s.");
		System.out.println("Using hash function set: " + hashset);

		Scanner scanner2 = null;
		scanner2 = new Scanner(file);

		FileWriter fw = null;

		try {
			fw = new FileWriter(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scanner2.hasNextLine()) {
			String line = scanner2.nextLine();
			if (line.charAt(0) == '@') {
				line = scanner2.nextLine();

				for (int j = 0; j < line.length() - 15; j++) {

					final String kmer = line.substring(j, j + 15);
					int count = sketch.query(kmer);
					fw.write(kmer + " " + count + "\n");

				}

			}
		}

		fw.flush();
		fw.close();

	}

}
