/**
 * 
 */
package edu.jhu.cs.cs439.project.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class sets a threshould and compares the difference that exact count and count-min sketch 
 * determine solid k-mers. We use the result of exact count as reference and calculate the false-positive
 * and false-negative rate for the threshould we choose.
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 1.0
 * @since 1.0
 */
public class EvaluateCount2 {

	public static void main(String[] args) throws IOException {

		String exact = null;
		String cmcount = null;
		String output = null;

		if (args.length != 3) {

			System.out
					.println("Usage: EvaluateCount <Ref> <CountMin> <output>");

		} else {
			exact = args[0];
			cmcount = args[1];
			output = args[2];
		}

		Map<String, Integer> exactCount = new HashMap<>();
		Map<String, Integer> cmCount = new HashMap<>();

		Scanner scanner1 = new Scanner(new File(exact));
		Scanner scanner2 = new Scanner(new File(cmcount));

		while (scanner1.hasNextLine()) {

			String line = scanner1.nextLine();
			String key = line.split(" ")[0];
			Integer value = Integer.parseInt(line.split(" ")[1]);
			exactCount.put(key, value);

		}

		while (scanner2.hasNextLine()) {

			String line = scanner2.nextLine();
			String key = line.split(" ")[0];
			Integer value = Integer.parseInt(line.split(" ")[1]);
			cmCount.put(key, value);

		}

		// Write Evaluation result to output
		FileWriter out = null;
		try {
			out = new FileWriter(new File(output));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int threshould = 3;
		for (int i = 0; i < 20; i++) {
			int diff1 = 0;
			int diff2 = 0;
			String outputline = "Threshould for exact count:" + threshould
					+ "Threshould for count-min sketch:" + (threshould+i) ;
			try {
				out.write(outputline);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (String key : exactCount.keySet()) {
				int ec = 0;
				int cm1 = 0;
				if (exactCount.get(key) > threshould) {
					ec = 1;
				}
				if (cmCount.get(key) > (threshould+i)) {
					cm1 = 1;
				}
				if (cm1 - ec == 1) {
					diff1++;
				} else if (ec - cm1 == 1) {
					diff2++;
				}
				String outputLine = key + " " + "<R:" + ec + " CM :" + cm1
						+ ">" + "\n";
				try {
					out.write(outputLine);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			float falsePositive = (float) diff1 / (float) exactCount.size();
			float falseNegative = (float) diff2 / (float) exactCount.size();
			float errorRate = falsePositive + falseNegative;
			System.out.println("Number of distinct k-mers:" + exactCount.size());
			System.out.println("Threshould for exact count:" + threshould 
					+ "   Threshould for count-min sketch:" + (threshould+i));
			System.out.println("falsePositive = " + falsePositive);
			System.out.println("falseNegative = " + falseNegative);
			System.out.println("errorRate = " + errorRate);

		}
		out.flush();
		out.close();

	}

}
