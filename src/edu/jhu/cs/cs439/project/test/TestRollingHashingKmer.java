/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs439.project.RollingHashingKMers;

/**
 * This class is for testing the Rolling hashing functions
 * @author Yunlong Liu
 * @author Yijie   Li
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
		
	}

}
