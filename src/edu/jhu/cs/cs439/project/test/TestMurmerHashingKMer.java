/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs439.project.MurmerHashingKMers;

/**
 * This class is a junit test for testing MurmerHasher
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 1.0
 * @since   1.0
 */
public class TestMurmerHashingKMer extends TestCase {
	
	private MurmerHashingKMers hashing;

	@Before
	public void setUp() {
		hashing = new MurmerHashingKMers(3,15);
	}

	@Test
	public void testRollingHashingKmer() {

		String KMer_1 = "ACGTTGC";
		String KMer_2 = "ACGTTGC";
 		for (int i = 0; i < 3; i++) {
			int hashCode_1 = hashing.hashing(KMer_1, i);
			int hashCode_2 = hashing.hashing(KMer_2, i);
			System.out.println( hashCode_1);
			assertEquals( ( hashCode_1 >= 0 && hashCode_1 <= 14 ) , true);
			assertEquals( ( hashCode_1 >= 0 && hashCode_1 <= 14 ) , true);
			assertEquals( hashCode_1 , hashCode_2 );
		}
	}

}
