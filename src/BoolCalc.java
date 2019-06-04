/* Boolean Calculator
*  5/31/2019
*
*  This program takes a list of strings that contain logical predicates and
*  outputs a table of variables such that all predicates in the input
*  list are true.
*
*/ 

import java.util.*;
import java.util.stream.Stream;

import org.paukov.combinatorics3.Generator;

public class BoolCalc {
	
	public static void main(String[] args) {
		String predicate = "(A or B), (C or B)";
		String joined = predicate.replace(", " , " and ");
		deduce(joined);
		/*
		else {
			System.out.println("Please enter a valid string (Example: \"( (A and B) or (B or C) )\" ");
		}
		*/
	}

	private static void deduce(String predicate) {
		String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H","I",
							"J", "K", "L", "M","N", "O", "P","Q", "R",
							"S", "T", "U", "V", "W", "X", "Y", "Z"};

		
		Vector<String> varVec = new Vector<String>();
		Vector<Stream<?>> combos = new Vector<Stream<?>>();

		for (int i = 0; i < alphabet.length; i++){
			if (predicate.contains(alphabet[i]) == true) {
				varVec.add(alphabet[i]);
			}
		}

		int varLength = varVec.size();
		
		(Generator.permutation("0", "1")
	       .withRepetitions(varLength)
	       .stream())
	       .forEach(System.out::println);
		
		
		System.out.println();
		//System.out.println(varLength);
		//System.out.println(predicate);
		
	}
}
