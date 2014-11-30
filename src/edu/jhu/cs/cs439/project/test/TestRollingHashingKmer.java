/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs439.project.RollingHashingKMers;

/**
 * This class is for testing the Rolling hashing functions
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 * @version 0.0
 * @since 0.0
 */
public class TestRollingHashingKmer extends TestCase {

	private RollingHashingKMers hashing;

	@Before
	public void setUp() {
		hashing = new RollingHashingKMers(3, 15);
	}

	@Test
	public void testRollingHashingKmer() {

		String KMer_1 = "ACGTTGC";
		String KMer_2 = "ACGTTGC";
 		for (int i = 0; i < 3; i++) {
			int hashCode_1 = hashing.hashing(KMer_1, i);
			int hashCode_2 = hashing.hashing(KMer_2, i);
			assertEquals( ( hashCode_1 >= 0 && hashCode_1 <= 14 ) , true);
			assertEquals( ( hashCode_1 >= 0 && hashCode_1 <= 14 ) , true);
			assertEquals( hashCode_1 , hashCode_2 );
		}
	}

}
