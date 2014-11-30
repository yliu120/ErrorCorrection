/**
 * 
 */
package edu.jhu.cs.cs439.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to parse fasta file.
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since 0.0
 */
public class FastaParser {

	public static FastaParser SINGLETON = new FastaParser();
	
	public List<String> parseFasta( String filename ) {
		
		// A dynamic list to store strings
		List<String> reads = new ArrayList<>();
		
		// file scanner
		File file = new File( filename );
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println( "FastaParser: Fasta file not found!" );
		}
	
		// Scan the file line by line
		String sequence = "";
		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			if (line.contains(">")) {
				// finish one sequence
				if ( ! sequence.equals("") ) {
					reads.add(sequence);
					sequence = "";
				}
			} else {
				sequence += line;
			}
		}
		reads.add(sequence);
		
		scanner.close();
		
		return reads;
		
	}
}
