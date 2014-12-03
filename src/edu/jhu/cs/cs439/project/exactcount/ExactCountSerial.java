/**
 * 
 */
package edu.jhu.cs.cs439.project.exactcount;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Here we implements a serial version of K-mer exact counting
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class ExactCountSerial {

	public static void main(String[] args) throws IOException {

		if (args.length != 4) {
			System.out
					.println("Usage: CountKMersWithCountMinSerial <fq file> <output>");
			return;
		}

		final String fileName = args[0];
		final String output = args[1];
		Map<String, Integer> kmerCount = new HashMap<>();

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
					if (kmerCount.containsKey(kmer)) {
						int update = kmerCount.get(kmer) + 1;
						kmerCount.put(kmer, update);
					} else {
						kmerCount.put(kmer, 1);
					}

				}

			}
		}

		scanner.close();

		final long endTime = System.currentTimeMillis();
		System.out.println("Time using: " + (endTime - startTime) / 1000
				+ " s.");
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter( output );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for ( String key : kmerCount.keySet() ) {
			fw.write( key + " " + kmerCount.get(key) + "\n");
		}
		
		fw.flush();
		fw.close();

	}

}
