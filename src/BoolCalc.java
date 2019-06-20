/* Boolean Calculator
*  5/31/2019
*
*  This program takes a list of strings that contain logical predicates and
*  outputs a table of variables such that all predicates in the input
*  list are true.
*
*/ 

import java.util.*;
import java.lang.Math;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BoolCalc {
	
	public static void main(String[] args) {
		Vector<String> predicate = new Vector<String>(); 
		for(int i = 0; i < args.length; i++) {
			predicate.add(args[i]); 
		}

        	String joined = String.join(" ", predicate);
       		joined = joined.replace(",", " and ");
        	System.out.println(joined);
		deduce(joined);
	}

	private static void deduce(String predicate) {
		String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H","I",
							"J", "K", "L", "M","N", "O", "P","Q", "R",
							"S", "T", "U", "V", "W", "X", "Y", "Z"};
		
		Vector<String> varVec = new Vector<String>();

		for (int i = 0; i < alphabet.length; i++){
			if (predicate.contains(alphabet[i]) == true) {
				varVec.add(alphabet[i]);
			}
		}

		int varLength = varVec.size();
		
		String [][] combos = perumuationsWithRep(varLength);
		
		String tempPredicate = predicate;		
		
		List <String> trueList = new ArrayList<String>();
		
		String trueString="";
		
		for (int c = 0; c < combos.length; c++) { // loop that goes through the combos list
			for (int ic = 0; ic < varLength; ic++) {
				 tempPredicate = tempPredicate.replace(varVec.elementAt(ic), combos[c][ic]);
			}
			
			tempPredicate = tempPredicate.replaceAll("and", "&&");
			tempPredicate = tempPredicate.replaceAll("or", "||");
			//System.out.println(tempPredicate);
			try {
		           ScriptEngineManager sem = new ScriptEngineManager();
		           ScriptEngine se = sem.getEngineByName("JavaScript");
		           String myExpression = tempPredicate;
		           //System.out.println(se.eval(myExpression));
		           if (se.eval(myExpression) == (Object) 1) {
		        	   for (int x = 0; x < varLength; x++) {
		        		   trueString = trueString + combos[c][x];
		        	   }
		        	   trueList.add(trueString);
		        	   trueString = "";
		           }
		           }
			catch (ScriptException e) {

		           System.out.println("Invalid Expression");
		           e.printStackTrace();

		       }
			tempPredicate = predicate;
		}
		
		for (int y = 0; y < trueList.size(); y++) {
		}
		for (int j = 0; j < varLength; j++) {
			String tempString = varVec.elementAt(j) + ": [";
			for (int s = 0; s < trueList.size(); s++) {
				if (s == trueList.size() - 1) {
					tempString = tempString  + trueList.get(s).charAt(j);
				}
				else {
				tempString = tempString  + trueList.get(s).charAt(j) + ", ";
				}
			}
			tempString = tempString + "]";
			tempString = tempString.replace("0", "F");
			tempString = tempString.replace("1", "T");
			System.out.println(tempString);
			tempString = "";
		}
	}
	
	private static String[][] perumuationsWithRep(int rep) {
		Character tempLetter;
		int numCombos = (int)Math.pow(2, rep);
		String[][] combos = new String[numCombos][rep];
		for(int i = 0; i < numCombos; i++) {
			for (int k = 0; k < rep; k++) {
				tempLetter = decToBinary(i, rep).charAt(k);
				combos[i][k] = tempLetter.toString();
			}
		}
		return combos;
	}
	
	private static String decToBinary(int integer, int rep) {
		String binary = ""; 
				
		if (integer == 0) {
			for (int i = rep; i > 0; i--) {
				binary = binary + "0";
			}
		}
		
		else {		
			while (integer != 0) {
				int binaryNum = integer % 2;
				integer = integer / 2;
				binary = binary + binaryNum;
			}
			while (binary.length() < rep) {
				binary = binary + "0";
			}
		}
		
		binary = reverseString(binary);
		
		return binary;
	}
	
	private static String reverseString(String word) {
        int i;	// initialize the counter
        char[] reversed = new char[word.length()]; // initialize a character array for reversed word
        char[] expWord = word.toCharArray(); // initializes a character array for the word being reversed
        String reversedWord = " ";

        for (i = 0; i < word.length(); i++){ // loop to go through the character array
            reversed[i] = expWord[word.length()- 1 - i];
        }

        reversedWord = new String(reversed);
        return (reversedWord);
    }
	
}
