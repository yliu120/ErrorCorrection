/**
 * 
 */
package edu.jhu.cs.cs439.project.kmercountwithcmsketch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class implements a multi-threading version.
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class CountKMersWithCMMulThreads {
	
	public static void main(String[] args) {
		
		if ( args.length != 3 ) {
			System.out.println("Usage: CountKMersWithCountMinSerial <fq file> <delta> <epsilon>");
			return;
		}
		
		final String fileName = args[0];
		
		final double delta = Double.parseDouble( args[1] );
		final double epsilon = Double.parseDouble( args[2] );
		
		final int width = (int) Math.ceil(Math.E / epsilon);
		final int depth = (int) Math.ceil(Math.log(1 / delta));
		
		final CountMinSketchParameters sketch = new CountMinSketchParameters(depth, width, true);
		
		File file = new File( fileName );
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println( "Parsing fq file - file not found!" );
		}
		
		final long startTime = System.currentTimeMillis();

		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			if ( line.charAt(0) == '@' ) {
				line = scanner.nextLine();
			
				for ( int j = 0; j < line.length() - 15; j ++ ) {
					
					final String kmer = line.substring(j, j + 15);
					Thread[] threads = new Thread[ depth ];
					
					for ( int t = 0; t < depth ; t ++ ) {
						
						final int tt = t;
						
						threads[t] = new Thread("hasherThread") {

							@Override
							public void run() {
								
								int ww = sketch.getHashers().hashing(kmer, tt);
								synchronized( this ) {
									int update = sketch.getSketch()[tt][ww] + 1;
									sketch.setSketch(tt, ww, update);
								}
								
							}
							
						};
						threads[t].start();
						
					}
					
				}
			}
		}
		
		scanner.close();
		
		final long endTime = System.currentTimeMillis();
		
		System.out.println("Time using: " + (startTime-endTime)/1000 + " s.");
		sketch.printSketch();

	}

}
