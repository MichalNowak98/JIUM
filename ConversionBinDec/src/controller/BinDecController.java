/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import model.BinDecModel;
import view.BinDecView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Takes input from user through console, text file or command line arguments,
 * sends passed values to model and calls methods from model to convert values
 * or from view to show results and give information to user.
 * @author Michal Nowak
 * @version 2.2
 * @since 20-10-2019
 */
public class BinDecController implements Controller{
    /** Reference to view. */
    private BinDecView view;
    /** Reference to model. */
    private BinDecModel model;
    /** Reference to Scanner. */
    private Scanner scan;
    /** buffered input character stream. */
    private BufferedReader input;
    /** Formatted output character stream. */
    private PrintWriter output;
    /** server port number  */
    private int PORT;
    /** server address */
    private InetAddress hostAddress;
    /** a client id */
    private int id;
    
    /**
     * Constructor assigning <code>view</code> and <code>model</code> to object fields.
     * @param view Reference to object representing user interface of application
     */
    public BinDecController(BinDecView view, int PORT, InetAddress hostAddress, int id, BufferedReader input, PrintWriter output)
    {
        this.view = view;
        scan = new Scanner(System.in);
        this.PORT = PORT;
        this.hostAddress = hostAddress;
        this.id = id;
        this.input = input;
        this.output = output;
    }    
    /**
     * Converts passed String representations of decimal or binary values
     * @param value undefined number of String representations of numbers to be converted
     */
    @Override
    public void convert(String ... value) {
        try {
            String msg, rcvd;
            //sending values to convert
            for (String val : value) {
                msg = "SEND " + val;
                output.println(msg);
                //confirmation
                rcvd = input.readLine();
                view.showConfirmationMsg(rcvd);
            }
            //sending command CONV to get converted values
            msg = "CONV";
            output.println(msg);
            rcvd = input.readLine();
            System.out.println(rcvd);
            //getting number of elements converted
            rcvd = input.readLine();
            int numberOfElements = Integer.parseInt(rcvd);
            //printing results from server
            for(int i = 0; i < numberOfElements; i++) {
                //data
                rcvd = input.readLine();
                view.showNumberBeforeConversion(rcvd);
                rcvd = input.readLine();
                view.showNumberAfterConversion(rcvd);
            }
        } catch (IOException e) {
            System.err.println("Error during communication!");
        }
    }
    /**
     * Runs user interface in console, letting user to type desired number to be converted or path to .txt file with numbers to be converted in it. Typing 'q' results in exiting loop and function. 
     */
    @Override
    public void runConsoleInterface () {
        boolean usedByUser = true;
        while (usedByUser) {
            char choice = view.askForChoice();
            switch (choice) {
                case '1': {
                    convert(view.askForInput());
                    break;
                }
                case '2': {
                    List<String> values = new ArrayList();
                    Scanner fileScanner;
                    File file = new File(view.askForFilePath());
                    try {
                        fileScanner = new Scanner(file);
                        while(fileScanner.hasNext()) {
                            values.add(fileScanner.next());
                        }
                        convert(values.toArray(new String[0]));
                    }
                    catch (FileNotFoundException ex) {
                        view.logException(ex.getMessage());
                    }
                    break;
                }
                case '3': {
                    try{
                        String cmd = "HELP", rcvd;
                        output.println(cmd);
                        rcvd = input.readLine();
                        view.showNumberAfterConversion(rcvd);
                    } catch (IOException e) {
                        System.err.println("Error during communication!");
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
