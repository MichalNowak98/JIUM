/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controller.BinDecController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import view.BinDecView;

/**
 *
 * @author Michal Nowak
 */
public class ConversionBinDecClientUDP extends Thread{
    /** communication socket */
    private DatagramSocket datagramSocket;
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
    
    private BinDecView view;
    private BinDecController controller;
    
    private static List<String> valuesToConvert = new ArrayList();
    
    private static void setValuesToConvert(String ... args) {
        valuesToConvert.addAll(Arrays.asList(args));
    }
    
    /** 
    * The constructor of the UDP client object
    * @param id client identifier
    */
    public ConversionBinDecClientUDP(int id) {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(".properties")) {
            properties.load(in);
            PORT = Integer.parseInt(properties.getProperty("ClientPORT"));
            String hostname = properties.getProperty("hostAdress");
            hostAddress = InetAddress.getByName(hostname);
        } catch (Exception e) {
            PORT = 1998;
            properties.setProperty("ClientPORT", new StringBuilder().append(PORT).toString());
            properties.setProperty("hostAddress", "localhost");
            //try{
                //properties.setProperty("hostAddress", InetAddress.getByName("localhost").toString());
            //} catch (UnknownHostException ex) {
            //    System.err.println("Unknown server!");
            //} 
            try (FileOutputStream out = new FileOutputStream(".properties")) {
                properties.store(out, "--Client configuration--");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
           
        this.id = id;
        view = new BinDecView();
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Connection is not available!");
        }
        System.out.println("Client " + id + " started");
        controller = new BinDecController(view, datagramSocket, PORT, hostAddress, buf, dp, id);
    }
    
    @Override
    public void run() {
        controller.convert(valuesToConvert.toArray(new String[0]));
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
        new ConversionBinDecClientUDP(1).start();
    }
}
