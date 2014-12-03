/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import scala.Tuple2;

/**
 * This class implements the serial algorithm: Counting K-mers using count-min
 * sketch
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class CountKMersWithCountMinSerial {

	public static void main(String[] args) throws IOException {

		if (args.length != 4) {
			System.out
					.println("Usage: CountKMersWithCountMinSerial <fq file> <delta> <epsilon> <output>");
			return;
		}

		final String fileName = args[0];
		final String output   = args[3];

		final double delta = Double.parseDouble(args[1]);
		final double epsilon = Double.parseDouble(args[2]);

		final int width = (int) Math.ceil(Math.E / epsilon);
		final int depth = (int) Math.ceil(Math.log(1 / delta));

		final CountMinSketchParameters sketch = new CountMinSketchParameters(
				depth, width, true);

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

		scanner.close();
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Time using: " + (endTime-startTime)/1000 + " s.");
		
		final long phase2 = System.currentTimeMillis();
		
		Scanner scanner2 = null;
		scanner2 = new Scanner( file );
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter( output );
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
					int count = sketch.query( kmer );
					fw.write( kmer + " " + count + "\n");

				}

			}
		}
		
		fw.flush();
		fw.close();

	}

}
