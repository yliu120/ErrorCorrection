/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import junit.framework.TestCase;

import org.junit.Test;

import edu.jhu.cs.cs439.project.FastaParser;

/**
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class TestFastaParser extends TestCase {

	@Test
	public void testParserUsingOneSequence() {

		String result = "LCLYTHIGRNIYYGSYLYSETWNTGIMLLLITMATAFMGYVLPWGQMSFWGATVITNLFSAIPYIGTNLV"
				+ "EWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIPFHPYYTIKDFLG"
				+ "LLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRSVPNKLGGVLALFLSIVIL"
				+ "GLMPFLHTSKHRSMMLRPLSQALFWTLTMDLLTLTWIGSQPVEYPYTIIGQMASILYFSIILAFLPIAGX"
				+ "IENY";
		String[] test = FastaParser.SINGLETON.parseFasta("data/test.fasta");
		assertEquals(test.length, 1);
		assertEquals(test[0], result);
	}
	
	@Test
	public void testMultiSequence() {
		
		String result = "LCLYTHIGRNIYYGSYLYSETWNTGIMLLLITMATAFMGYVLPWGQMSFWGATVITNLFSAIPYIGTNLV"
				+ "EWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIPFHPYYTIKDFLG"
				+ "LLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRSVPNKLGGVLALFLSIVIL"
				+ "GLMPFLHTSKHRSMMLRPLSQALFWTLTMDLLTLTWIGSQPVEYPYTIIGQMASILYFSIILAFLPIAGX"
				+ "IENY";
		String[] test = FastaParser.SINGLETON.parseFasta("data/testMul.fasta");
		
		assertEquals(test.length, 5);
		assertEquals(test[0], result);
		assertEquals(test[4], result);
		
	}
	
}
