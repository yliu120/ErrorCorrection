/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs439.project.KMerHashers;

/**
 * This class tests the hasher functions that we get.
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 0.0
 * @since 0.0
 */
public class TestKMerHashers extends TestCase {

	private KMerHashers hashers;

	@Before
	public void setUp() {

		hashers = new KMerHashers(3, 15);

	}

	/**
	 * Medium test for Dhashers.
	 */
	@Test
	public void testDhashers() {

		List<Map<String, String>> functions = hashers.getHashFunctions();
		assertEquals(functions.size(), 3);
		
		Set<String> test = new HashSet<>();

		for (int i = 0; i < 3; i++) {
			String hashValues = "";
			for (String string : functions.get(0).keySet()) {
				hashValues += functions.get(0).get(string);
			}
			assertEquals(hashValues.contains("00"), true);
			assertEquals(hashValues.contains("10"), true);
			assertEquals(hashValues.contains("01"), true);
			assertEquals(hashValues.contains("11"), true);
			
			test.add(hashValues);
		}
		
		assertEquals( test.size(), 3 );

	}
}
