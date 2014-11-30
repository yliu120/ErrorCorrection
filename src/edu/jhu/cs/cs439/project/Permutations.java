package edu.jhu.cs.cs439.project;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *  Compilation:  javac Permutations.java
 *  Execution:    java Permutations N
 *  
 *  Enumerates all permutations on N elements.
 *  Two different approaches are included.
 *
 *  % java Permutations 3
 *  abc
 *  acb
 *  bac 
 *  bca
 *  cab
 *  cba
 *
 * Note that this class is provide by
 * 
 *      http://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
 *      
 * Modified by Yunlong Liu
 *************************************************************************/

public class Permutations {
	
	private List<String> acgt;

    /**
     * Default Constructor
	 */
	public Permutations() {
		super();
		this.acgt = new ArrayList<String>();
	}

	/**
	 *  print N! permutation of the elements of array a (not in order)
	 */
    public void perm2(String s) {
       int N = s.length();
       char[] a = new char[N];
       for (int i = 0; i < N; i++)
           a[i] = s.charAt(i);
       perm2(a, N);
    }
    
	/**
	 * Getters to return the permuted ACGT.    
	 * @return a list of permutations
	 */
    public List<String> getAcgt() {
		return acgt;
	}

	private void perm2(char[] a, int n) {
        if (n == 1) {
            this.acgt.add( new String( a ) );
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            perm2(a, n-1);
            swap(a, i, n-1);
        }
    }  

    // swap the characters at indices i and j
    private void swap(char[] a, int i, int j) {
        char c;
        c = a[i]; a[i] = a[j]; a[j] = c;
    }

}

