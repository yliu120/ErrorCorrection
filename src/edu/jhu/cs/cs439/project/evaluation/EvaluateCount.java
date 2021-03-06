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
 * We implement a naive serial version of the evaluation
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 1.0
 * @since 1.0
 */
public class EvaluateCount {

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
		Map<Integer, Integer> statCount = new HashMap<>();

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

		Map<Integer, Integer> exactCountStat = new HashMap<>();

		out.write("Evaluation of  " + args[1] + "\n");
		out.write("-----------------------------" + "\n");
		out.write("Count difference detail summary:" + "\n");

		for (String key : exactCount.keySet()) {

			if (exactCountStat.containsKey(exactCount.get(key))) {
				exactCountStat.put(exactCount.get(key),
						exactCountStat.get(exactCount.get(key)) + 1);
			} else {
				exactCountStat.put(exactCount.get(key), 1);
			}

			int diff = cmCount.get(key) - exactCount.get(key);
			if (statCount.containsKey(diff)) {
				statCount.put(diff, statCount.get(diff) + 1);
			} else {
				statCount.put(diff, 1);
			}

			String outputLine = key + " " + "<R:" + exactCount.get(key)
					+ " CM:" + cmCount.get(key) + " D:" + diff + ">" + "\n";

			try {
				out.write(outputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		out.write("\n\n");
		out.write("-----------------------------------\n");
		out.write("Count difference statistics:" + "\n");
		out.write("-----------------------------------\n");

		for (Integer key : statCount.keySet()) {
			out.write(key.toString() + " " + statCount.get(key).toString()
					+ "\n");
		}
		
		out.write("\n\n");
		out.write("-----------------------------------\n");
		out.write("Kmer exact count statistics:" + "\n");
		out.write("-----------------------------------\n");
		
		for (Integer key : exactCountStat.keySet()) {
			out.write(key.toString() + " " + exactCountStat.get(key).toString()
					+ "\n");
		}

		out.flush();
		out.close();
	}

}
