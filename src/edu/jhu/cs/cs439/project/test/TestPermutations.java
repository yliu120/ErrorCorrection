/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs439.project.Permutations;

/**
 * This class is for testing ACGT 's permutations
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class TestPermutations extends TestCase {

	private Permutations per;

	@Before
	public void setUp() {

		per = new Permutations();

	}

	/**
	 * Very simple test to guarantee it is working
	 */
	@Test
	public void testPermutatedACGT() {

		per.perm2("ACGT");
		for (int i = 0; i < 24; i++) {
			assertEquals(per.getAcgt().get(i).length(), 4);
			assertEquals(per.getAcgt().get(i).contains("A"), true);
			assertEquals(per.getAcgt().get(i).contains("C"), true);
			assertEquals(per.getAcgt().get(i).contains("G"), true);
			assertEquals(per.getAcgt().get(i).contains("T"), true);
		}
		assertEquals(per.getAcgt().size(), 24);

	}

}
