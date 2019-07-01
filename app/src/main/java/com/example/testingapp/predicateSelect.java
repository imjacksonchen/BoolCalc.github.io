package com.example.testingapp;

// Packages imported
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;
import java.lang.Math;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class predicateSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicate_select);

        Button submitButton = findViewById(R.id.submitButton);
        final TextView results = findViewById(R.id.results);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText boolExpression = findViewById(R.id.boolExpression);
                String strBoolExpression = boolExpression.getText().toString();
                if (strBoolExpression.isEmpty()){
                    results.setText("Box cannot be empty!");
                }
                else {
                    strBoolExpression = strBoolExpression.replace(",", " and");
                    String trueResults = deduce(strBoolExpression);
                    results.setText(trueResults);
                }
            }
        });

    }

    private static String deduce(String predicate){

        // Local Variables
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H","I",
                "J", "K", "L", "M","N", "O", "P","Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Vector<String> varVec = new Vector<String>();
        String tempPredicate = predicate;
        List <String> trueList = new ArrayList<String>();
        String completeString = "";
        String trueString = "";

        for (int i = 0; i < alphabet.length; i++){
            if (predicate.contains(alphabet[i])) {
                varVec.add(alphabet[i]);
            }
        }

        int varLength = varVec.size();
        String [][] combos = permutationsWithRep(varLength);

        for (int c = 0; c < combos.length; c++) { // loop that goes through the combos list
            for (int ic = 0; ic < varLength; ic++) {
                tempPredicate = tempPredicate.replace(varVec.elementAt(ic), combos[c][ic]);
            }

            tempPredicate = tempPredicate.replaceAll("and", "&&");
            tempPredicate = tempPredicate.replaceAll("or", "||");

            try {
                ScriptEngineManager sem = new ScriptEngineManager();
                ScriptEngine engine = sem.getEngineByName("rhino");
                String myExpression = tempPredicate;
                if (engine.eval(myExpression).equals(engine.eval("1.0")) ) {
                    for (int x = 0; x < varLength; x++) {
                        trueString = trueString + combos[c][x];
                    }
                    trueList.add(trueString);
                }
            }
            catch(ScriptException e) {
                e.printStackTrace();
                return "Invalid Expression";
            }
            tempPredicate = predicate;
            trueString = "";
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
            completeString = completeString + "\n" + tempString;
        }

        return completeString;
    }

    private static String[][] permutationsWithRep(int rep) {
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
