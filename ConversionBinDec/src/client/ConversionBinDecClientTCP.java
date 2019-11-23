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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import view.BinDecView;

/**
 * @author Michal
 */
public class ConversionBinDecClientTCP extends Thread{
    /** server port number  */
    static int PORT;
    /** server address */
    private InetAddress hostAddress;
    /** buffer for input data */
    private byte[] buf = new byte[1024];
    /** data frame */
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    /** a client id */
    private int id;
    /** buffered input character stream. */
    private BufferedReader input;
    /** Formatted output character stream. */
    private PrintWriter output;
    /** socket representing connection to the client. */
    private Socket socket;
    
    private BinDecView view;
    
    private BinDecController controller;
    
    private static List<String> valuesToConvert = new ArrayList();
    
    private static void setValuesToConvert(String ... args) {
        valuesToConvert.addAll(Arrays.asList(args));
    }
    
    private ConversionBinDecClientTCP(int id){
        Properties properties = new Properties();
        view = new BinDecView();
        try (FileInputStream in = new FileInputStream(".properties")) {
            properties.load(in);
            PORT = Integer.parseInt(properties.getProperty("ClientPORT"));
            String hostname = properties.getProperty("hostAdress");
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
        this.id = id;
        try {
            socket = new Socket("localhost",this.PORT);
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
        controller = new BinDecController(view, PORT, hostAddress, buf, dp, id, input, output);
    }
    
    @Override
    public void run() {
        try {
            String rcvd = input.readLine();
            System.out.println(rcvd);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        //controller.convert(valuesToConvert.toArray(new String[0]));
        controller.runConsoleInterface();
    }

    /** 
     * The main application method 
     * @param args Contains numbers for conversion, both decimal and binary. Order does not matter.
     * @author Michal Nowak
     * @version 2.0 
     * @since 20-10-2019
     */
    public static void main(String[] args) {
        setValuesToConvert(args);
        new ConversionBinDecClientTCP(1).start();
    }
}
