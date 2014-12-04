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
		
		int diff = 0;
		for (String key : exactCount.keySet()) {
			String ec = null;
			String cm = null;
			if (exactCount.get(key) > 3){
				ec = "1";
			} else {
				ec = "0";
			}
			if (cmCount.get(key)>3){
				cm = "1";
			} else{
				cm = "0";
			}
			if (!cm.equalsIgnoreCase(ec)){
				diff++;
			}
			String outputLine = key + " " + "<R:" + ec
					+ " CM:" +cm  + ">" + "\n";
			try {
				out.write(outputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		float errorRate = (float)diff/(float)exactCount.size();
		System.out.print("Error rate = " + errorRate + "\n");
		out.flush();
		out.close();

	}

}
