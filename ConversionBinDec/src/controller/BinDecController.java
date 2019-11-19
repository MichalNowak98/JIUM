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
 * sends passed values to model and calls methods from model to convert values
 * or from view to show results and give information to user.
 * @author Michal Nowak
 * @version 2.1
 * @since 20-10-2019
 */
public class BinDecController implements Controller{
    /** Reference to view. */
    private BinDecView view;
    /** Reference to model. */
    private BinDecModel model;
    /** Reference to Scanner. */
    private Scanner scan;
    
    /* public methods */
    /**
     * Constructor assigning <code>view</code> and <code>model</code> to object fields.
     * @param view Reference to object representing user interface of application
     * @param model Reference to object representing model of application
     */
    public BinDecController(BinDecView view, BinDecModel model)
    {
        this.view = view;
        this.model = model;
        scan = new Scanner(System.in);
    }    
    /**
     * Converts passed String representations of decimal or binary values
     * @param value undefined number of String representations of numbers to be converted
     */
    @Override
    public void convert(String ... value) {
        for (String val : value) {
            try{
                model.addValue(val);
            } catch (IncorrectNumberException ex){
                view.logException(ex.getMessage());
            }
        }
        for(int i = 0; i < model.getValuesSize(); i++) {
            convertModelValue(i);
        }
        model.clearValues();
    }
    /**
     * Converts element at <code>index</code> to binary or decimal numeral system
     * @param index Index for values list
     */
    private void convertModelValue(int index) {
        String val = model.getValue(index);
        view.showNumberBeforeConversion(val);
        //value have to have at least 3 characters to be binary number: 0b0 or 0b1
        if(val.length() < 3) {
            model.addConvertedValue(model.convertDecToBin(index));
        }
        else if(val.charAt(0) == '0' && (val.charAt(1) == 'b' || val.charAt(1) == 'B')) {
            model.addConvertedValue(model.convertBinToDec(index));
        }
        else {
            model.addConvertedValue(model.convertDecToBin(index));
        }
        view.showNumberAfterConversion(model.getConvertedValue(index));
    }
    /**
     * Runs user interface in console, letting user to type desired number to be converted or path to .txt file with numbers to be converted in it. Typing 'q' results in exiting loop and function. 
     */
    @Override
    public void runConsoleInterface () {
        boolean usedByUser = true;
        while (usedByUser) {
            view.askForChoice();
            char choice = scan.nextLine().charAt(0);
            switch (choice) {
                case '1': {
                    view.askForInput();
                    convert(scan.nextLine());
                    break;
                }
                case '2': {
                    view.askForFilePath();
                    Scanner fileScanner;
                    File file = new File(scan.nextLine());
                    /* throws FileNotFoundException */
                    try {
                        fileScanner = new Scanner(file);
                        while(fileScanner.hasNext()) {
                            convert(fileScanner.next());
                        }
                    }
                    catch (FileNotFoundException ex) {
                        view.logException(ex.getMessage());
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
}
