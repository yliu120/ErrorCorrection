
package edu.jhu.cs.cs439.project.projectinterface;

import java.util.List;
import java.util.Map;

/**
 * This interface will provide public reference for different hashers;
 * @author Yunlong Liu
 * @author Yijie   Li
 * @version 0.0
 * @since 0.0
 */
public interface Hashers {
	
	/**
	 * Please implement this function to give hash functions.
	 * @return a list of maps as hashers
	 */
	public List<Map<String, String>> getHashFunctions();

}
