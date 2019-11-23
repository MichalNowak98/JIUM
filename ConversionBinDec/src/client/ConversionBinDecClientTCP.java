/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controller.BinDecController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import view.BinDecView;

/**
 * Client TCP. Connects to TCP server and allows to 
 * @author Michal
 */
public class ConversionBinDecClientTCP extends Thread{
    /** Server port number.  */
    int PORT;
    /** Server address. */
    private InetAddress hostAddress;
    /** Client id. */
    private int id;
    /** Buffered input character stream. */
    private BufferedReader input;
    /** Formatted output character stream. */
    private PrintWriter output;
    /** Socket of a client. */
    private Socket socket;
    /** Reference to view component. */
    private BinDecView view;
    /** Reference to controller component. */
    private BinDecController controller;
    /** Name of the host server. */
    private String hostname;
    /** List containing values to send to server. */
    private static List<String> valuesToConvert = new ArrayList();
    
    /**
     * Sets <code>valuesToConvert</code> with values to convert.
     * @param args Values to convert.
     */
    private static void setValuesToConvert(String ... args) {
        valuesToConvert.addAll(Arrays.asList(args));
    }
    
    /**
     * Constructor taking id of a client. Creates view and controller instances,
     * gets or sets <code>hostName</code> and <code>PORT</code> from 
     * <code>.properties</code> file and sets all fields important for connection.
     * @param id id of a client.
     */
    public ConversionBinDecClientTCP(int id){
        view = new BinDecView();
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(".properties")) {
            properties.load(in);
            PORT = Integer.parseInt(properties.getProperty("ClientPORT"));
            hostname = properties.getProperty("hostAdress");
            hostAddress = InetAddress.getByName(hostname);
        } catch (Exception e) {
            PORT = 1998;
            properties.setProperty("ClientPORT", new StringBuilder().append(PORT).toString());
            properties.setProperty("hostAddress", "localhost");
            
            try (FileOutputStream out = new FileOutputStream(".properties")) {
                properties.store(out, "--Client configuration--");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        try {
            socket = new Socket(hostname, this.PORT);
            output = new PrintWriter(
                new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream())), true);
            input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        controller = new BinDecController(view, PORT, hostAddress, id, input, output);
        this.id = id;
    }
    
    /**
     * Prints connection message from server, passes command line arguments to 
     * server and runs console user interface.
     */
    @Override
    public void run() {
        try {
            String rcvd = input.readLine();
            System.out.println(rcvd);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        controller.convert(valuesToConvert.toArray(new String[0]));
        controller.runConsoleInterface();
    }

    /** 
     * The main application method. Creates and starts instance of client.
     * @param args Contains numbers for conversion, both decimal and binary.
     * Order does not matter.
     * @author Michal Nowak
     * @version 3.0 
     * @since 24-11-2019
     */
    public static void main(String[] args) {
        setValuesToConvert(args);
        new ConversionBinDecClientTCP(1).start();
    }
}
