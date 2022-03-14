/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package br.edu.cefsa.compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

/**
 *
 * @author losan
 */
public class Compiladores {
   
    static String validInitialCharacters = "{[(<";
    static String validFinalCharacters = "}])>";
    
    public static void main(String[] args) throws Exception {
        String filePath = args.length == 0 ? "prog.txt" : args[0];
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("O arquivo não foi encontrado. Por favor insirqa na mesma pasta em que o arquivo .java");
            return;
        }
        
        StringBuilder inputText = readTextFile(filePath);
        StringBuilder outputText = generateOutputText(inputText);
        writeToExitFile(outputText);

    }

    private static StringBuilder readTextFile(String filePath) throws Exception {
        StringBuilder inputText;
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            inputText = new StringBuilder();
            while ((line = br.readLine()) != null) {
                inputText.append(line);
                inputText.append("\n");
            }
        }
        return inputText;
    }

    private static StringBuilder generateOutputText(StringBuilder inputText) {
        StringBuilder outputText = new StringBuilder();
        for(String line : inputText.toString().split("\n")){
            outputText.append(line).append(" - ");
            outputText.append( isLineValid(line) ? "OK" : "Inválido" );
            outputText.append("\n");
        }
        return outputText;
    }

    private static boolean isLineValid(String line) {
        if(!areCharactersValid(line))
            return false;
        if(!areCharactersCountMatching(line))
            return false;
        if(!isCharactersMatching(line))
            return false;
        return true;
    }

    private static boolean areCharactersValid(String line) {
        for(char character : line.toCharArray())
        {
            if(validFinalCharacters.indexOf(character) == -1 && validInitialCharacters.indexOf(character) == -1)
                return false;
        }
        
        return true;
    }
    
    private static boolean areCharactersCountMatching(String line) {
        if(line.chars().filter(ch -> ch == validInitialCharacters.charAt(0)).count() != line.chars().filter(ch -> ch == validFinalCharacters.charAt(0)).count())
            return false;
        if(line.chars().filter(ch -> ch == validInitialCharacters.charAt(1)).count() != line.chars().filter(ch -> ch == validFinalCharacters.charAt(1)).count())
            return false;
        if(line.chars().filter(ch -> ch == validInitialCharacters.charAt(2)).count() != line.chars().filter(ch -> ch == validFinalCharacters.charAt(2)).count())
            return false;
        
        return true;
    }
    
    private static boolean isCharactersMatching(String line) {
        Stack<String> stack = new Stack<String>();
        for(char character : line.toCharArray())
        {
            
            if(validFinalCharacters.indexOf(character) != -1 && stack.empty())
                return false;
            
            if(validFinalCharacters.indexOf(character) != -1 && stack.lastElement().charAt(0) != validInitialCharacters.charAt(validFinalCharacters.indexOf(character)))
                return false;
            
            if(validInitialCharacters.indexOf(character) != -1)
                stack.add(String.valueOf(character));
            else
                stack.pop();      
        }
        return true;
    }

    private static void writeToExitFile(StringBuilder outputText) throws Exception {
        File file = new File("prog-check.txt");
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(outputText.toString());
        }
    }
}
