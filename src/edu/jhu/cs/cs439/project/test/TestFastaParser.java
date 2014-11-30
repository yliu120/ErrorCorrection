/**
 * 
 */
package edu.jhu.cs.cs439.project.test;

import java.util.List;

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
		List<String> test = FastaParser.SINGLETON.parseFasta("data/test.fasta");
		assertEquals(test.size(), 1);
		assertEquals(test.get(0), result);
	}
	
	@Test
	public void testMultiSequence() {
		
		String result = "LCLYTHIGRNIYYGSYLYSETWNTGIMLLLITMATAFMGYVLPWGQMSFWGATVITNLFSAIPYIGTNLV"
				+ "EWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIPFHPYYTIKDFLG"
				+ "LLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRSVPNKLGGVLALFLSIVIL"
				+ "GLMPFLHTSKHRSMMLRPLSQALFWTLTMDLLTLTWIGSQPVEYPYTIIGQMASILYFSIILAFLPIAGX"
				+ "IENY";
		List<String> test = FastaParser.SINGLETON.parseFasta("data/testMul.fasta");
		
		assertEquals(test.size(), 5);
		assertEquals(test.get(0), result);
		assertEquals(test.get(4), result);
		
	}
	
}
