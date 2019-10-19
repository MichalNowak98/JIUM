/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.BinDecModel;
import view.BinDecView;
import model.IncorrectNumberException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Takes input from user through console, text file or command line arguments,
 * converts passed values, stores them in model and calls methods from view to show 
 * results and inform user
 * @author Michal
 * @version %I%, %G% 
 * @since
 */
public class BinDecController {
    /**
     * Reference to view.
     */
    private final BinDecView view;
    /**
     * Reference to model.
     */
    private final BinDecModel model;
    /**
     * Reference to scanner.
     */
    private final Scanner scan;
    
    /* public methods */
    /**
     * Constructor assigning view and model to object.
     * @param view Reference to object representing GUI of application
     * @param model Reference to object representing model of application
     */
    public BinDecController(BinDecView view, BinDecModel model)
    {
        this.view = view;
        this.model = model;
        scan = new Scanner(System.in);
    }
    /**
     * Converts value from decimal numeral system to binary numeral system or from binary numeral system to decimal numeral system.
     * @param value String representation of value to be converted.
     */
    public void convert(String value) {
        try {
            model.setValue(value);
            this.view.showNumberBeforeConversion(model.getValue());
            if(value.length() < 3) {
                model.setValue(convertDecToBin(value));
            }
            else if(value.charAt(0) == '0' && (value.charAt(1) == 'b' || value.charAt(1) == 'B')) {
                model.setValue(convertBinToDec(value));
            }
            else {
                model.setValue(convertDecToBin(value));
            }
            this.view.showNumberAfterConversion(model.getValue());
        }
        catch (IncorrectNumberException ex){
            this.view.logException(ex.getMessage());
        }
    }
    
    /**
     * Converts all elements of passed table from decimal numeral system to binary numeral system or from binary numeral system to decimal numeral system (can be both decimal and binary in one array).
     * @param arraySize Size of passed array.
     * @param values Array of String representations of values to be converted.
     */
    public void convert(int arraySize, String values[]) {
        for(int index = 0; index < arraySize; index++) {
            try {
                model.setValue(values[index]);
                this.view.showNumberBeforeConversion(model.getValue());
                if(values[index].length() < 3) {
                    model.setValue(convertDecToBin(values[index]));
                }
                if(values[index].charAt(0) == '0' && (values[index].charAt(1) == 'b' || values[index].charAt(1) == 'B')) {
                    model.setValue(convertBinToDec(values[index]));
                }
                else {
                    model.setValue(convertDecToBin(values[index]));
                }
                this.view.showNumberAfterConversion(model.getValue());
            }
            catch (IncorrectNumberException ex){
                this.view.logException(ex.getMessage());
            }
        }
    }
    /**
     * Runs graphical user interface in console, that lets user to type desired number to be converted or path to .txt file with numbers to be converted in it. Typing 'q' results in exiting loop and function. 
     */
    public void runConsoleInterface () {
        boolean usedByUser = true;
        while (usedByUser) {
            this.view.askForChoice();
            char choice = scan.nextLine().charAt(0);
            switch (choice) {
                case '1': {
                    BinDecController.this.convert(scan.nextLine());
                    break;
                }
                case '2': {
                    Scanner fileScanner;
                    File file = new File(scan.nextLine());
                    /* throws FileNotFoundException */
                    try {
                        fileScanner = new Scanner(file);
                        while(fileScanner.hasNext()) {
                            BinDecController.this.convert(fileScanner.next());
                        }
                    }
                    catch (FileNotFoundException ex) {
                        this.view.logException(ex.getMessage());
                    }
                    break;
                }
                case 'q': {
                    usedByUser = false;
                    break;
                }
                default : {
                    /* Assumption: do nothing */
                    break;
                }
            }
        }
    }
    
    /* private methods */
    /**
     * Takes input from user.
     * @return Value passed by user.
     */
    private String takeInputFromConsole() {
        String value = scan.nextLine();
        return value;
    }
    /**
     * Converts value from decimal numeral system to binary numeral system.
     * @param decValue String representation of value to be converted.
     * @return String representation of converted value.
     */
    private String convertDecToBin(String decValue) {
        int binValue = Integer.parseInt(decValue);
        return "0b" + Integer.toBinaryString(binValue);
    }
    /**
     * Converts from binary numeral system to decimal numeral system.
     * @param binValue String representation of value to be converted.
     * @return String representation of converted value.
     */
    private String convertBinToDec(String binValue) {
        int multiplier = 1;
        int decValue = 0;
        for (int i = binValue.length() - 1; i > 1; i--) {
            decValue += Character.getNumericValue(binValue.charAt(i)) * multiplier;
            multiplier *= 2;
        }
        return Integer.toString(decValue);
    }
}
