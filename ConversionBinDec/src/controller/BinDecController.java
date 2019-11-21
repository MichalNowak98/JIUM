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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    /** communication socket */
    private DatagramSocket datagramSocket;
    /** server port number  */
    private int PORT;
    /** server address */
    private InetAddress hostAddress;
    /** buffer for input data */
    private byte[] buf;
    /** data frame */
    private DatagramPacket dp;
    /** a client id */
    private int id;
    
    /* public methods */
    /**
     * Constructor assigning <code>view</code> and <code>model</code> to object fields.
     * @param view Reference to object representing user interface of application
     */
    public BinDecController(BinDecView view, DatagramSocket datagramSocket, int PORT, InetAddress hostAddress, byte[] buf, DatagramPacket dp, int id)
    {
        this.view = view;
        scan = new Scanner(System.in);
        this.datagramSocket = datagramSocket;
        this.PORT = PORT;
        this.hostAddress = hostAddress;
        this.buf = buf;
        this.dp = dp;
        this.id = id;
    }    
    /**
     * Converts passed String representations of decimal or binary values
     * @param value undefined number of String representations of numbers to be converted
     */
    @Override
    public void convert(String ... value) {
        try {
            String rcvd;
            for (String val : value) {
                buf = val.getBytes();
                datagramSocket.send(new DatagramPacket(buf, buf.length, hostAddress, PORT));
                //confirmation
                datagramSocket.receive(dp);
                view.showConfirmationMsg(new String(dp.getData(), 0, dp.getLength()));
                //data
                datagramSocket.receive(dp);
                view.showNumberBeforeConversion(val);
                view.showNumberAfterConversion(new String(dp.getData(), 0, dp.getLength()));
            }                
        } catch (IOException e) {
            System.err.println("Error during communication!");
        }
    }
    /**
     * Converts element at <code>index</code> to binary or decimal numeral system
     * @param index Index for values list
     */
    private void convertModelValue(int index) {
        String val = model.getValue(index);
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
                    Scanner fileScanner;
                    File file = new File(view.askForFilePath());
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
