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
 * @author hduser
 *
 */
public class FastqReadsParser {
	
	public static FastqReadsParser SINGLETON = new FastqReadsParser();
	
	public List<String> parseFastq( String filename ) {
		
		// A dynamic list to store strings
		List<String> reads = new ArrayList<>();
		
		// file scanner
		File file = new File( filename );
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println( "FastqReadsParser: Fasta file not found!" );
		}

		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			if ( line.charAt(0) == '@' ) {
				line = scanner.nextLine();
				reads.add(line);
			}
		}
		
		scanner.close();
		
		return reads;
		
	}

}
