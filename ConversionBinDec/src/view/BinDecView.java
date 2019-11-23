/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import java.util.Scanner;

/**
 * Contains variety of methods giving user informations about results and program options.
 * @author Michal Nowak
 * @version 2.2
 * @since 20-10-2019
 */
public class BinDecView {
    private Scanner scan;
    
    public BinDecView() {
        scan = new Scanner(System.in);
    }
    /**
     * Prints "Enter value to be converted: " in console and returns value passed by user.
     * @return value passed by user
     */
    public String askForInput() {
        System.out.println("Enter value to be converted: ");
        return scan.nextLine();
    }
    /**
     * Prints "Enter path to text file storing values to be converted: " in console and returns path to file passed by user.
     * @return path to file passed by user.
     */
    public String askForFilePath() {
        System.out.println("Enter path to text file storing values to be converted: ");
        return scan.nextLine();
    }
    /**
     * Prints menu for user in console, showing available options, and returns character passed by user.
     * @return Character passed by user
     */
    public char askForChoice() {
        System.out.println("1:  type number for conversion");
        System.out.println("2:  give path to text file with numbers to convert");
        System.out.println("3:  HELP");
        System.out.println("q:  quit application");
        return scan.nextLine().charAt(0);
    }
    /**
     * Prints value before conversion in console followed by "=" sign. Designed to be used before showNumberAfterConversion(String value).
     * @param value Value to be printed.
     */
    public void showNumberBeforeConversion(String value) {
        System.out.print(value + " = ");
    }
    /**
     * Prints value after conversion in console. Designed to be used after showNumberBeforeConversion(String value).
     * @param value Value to be printed.
     */
    public void showNumberAfterConversion(String value) {
        System.out.println(value);
    }
    /**
     * Prints exception message in console.
     * @param errorMsg Message to be printed in console.
     */
    public void logException(String errorMsg) {
        System.out.println(errorMsg);
    }
    
    public void showResults(List<String> originalValues, List<String> convertedValues)
    {
        int i = 0;
        for(String value : originalValues) {
            System.out.println(value + " = " + convertedValues.get(i++));
        }
    }
    
    public void showResults(String[] originalValues, String[] convertedValues)
    {
        int i = 0;
        for(String value : originalValues) {
            System.out.println(value + " = " + convertedValues[i++]);
        }
    }
    
    public void showConfirmationMsg(String confMsg) {
        System.out.println("Server confirmation: " + confMsg);
    }
    
    public void showMsg(String msg){
        System.out.println(msg);
    }
}
