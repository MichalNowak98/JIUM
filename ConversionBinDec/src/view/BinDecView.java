/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * Contains variety of methods giving user informations about results and program options.
 * @author Michal Nowak
 * @version 2.0
 * @since 20-10-2019
 */
public class BinDecView {
    /**
     * Prints "Enter value to be converted: " in console.
     */
    public void askForInput() {
        System.out.println("Enter value to be converted: ");
    }
    /**
     * Prints "Enter path to text file storing values to be converted: " in console.
     */
    public void askForFilePath() {
        System.out.println("Enter path to text file storing values to be converted: ");
    }
    /**
     * Prints menu for user in console, showing available options.
     */
    public void askForChoice() {
        System.out.println("1:  type number for conversion");
        System.out.println("2:  give path to text file with numbers to convert");
        System.out.println("q:  quit application");
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
}
