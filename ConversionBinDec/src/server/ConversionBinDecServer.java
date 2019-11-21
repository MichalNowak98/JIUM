/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Properties;
import model.BinDecModel;
import model.IncorrectNumberException;

/**
 *
 * @author Michal
 */
public class ConversionBinDecServer {
    /** accessing port */
    static int PORT;
    /** buffer for input data */
    private byte[] buf = new byte[1024];
    /** data frame */
    private DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
    /** communication socket */
    private DatagramSocket socket;
    
    private BinDecModel model = new BinDecModel();

    /** 
     * The UDP server constructor 
     */
    public ConversionBinDecServer() {
        try {
            Properties properties = new Properties();
            try (FileInputStream in = new FileInputStream(".properties")) {
                properties.load(in);
                PORT = Integer.parseInt(properties.getProperty("ServerPORT"));           
            } catch (Exception e) {
                properties.setProperty("ServerPORT", "1998");
                PORT = 1998;
                try (FileOutputStream out = new FileOutputStream(".properties")) {
                    properties.store(out, "--Serwver configuration--");
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            socket = new DatagramSocket(PORT);
            System.out.println("Server started");
            while (true) {
                socket.receive(datagramPacket);
                String rcvd = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                String msg = "Got number " + rcvd + " to convert from" + ", from address: " + datagramPacket.getAddress() + ", port: " + datagramPacket.getPort();
                buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
                        datagramPacket.getAddress(), datagramPacket.getPort());
                socket.send(packet);
                try{
                    model.addValue(rcvd);
                } catch (IncorrectNumberException ex){
                    System.out.println(ex.getMessage());
                }
                
                if(rcvd.length() < 3) {
                    model.addConvertedValue(model.convertDecToBin(0));
                }
                else if(rcvd.charAt(0) == '0' && (rcvd.charAt(1) == 'b' || rcvd.charAt(1) == 'B')) {
                    model.addConvertedValue(model.convertBinToDec(0));
                }
                else {
                    model.addConvertedValue(model.convertDecToBin(0));
                }
                msg = model.getConvertedValue(0);
                model.clearValues();
                buf = msg.getBytes();
                packet = new DatagramPacket(buf, buf.length,
                        datagramPacket.getAddress(), datagramPacket.getPort());
                socket.send(packet);
            }
        } catch (SocketException e) {
            System.err.println("Connection is not available!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error during connection!");
            e.printStackTrace();
        }
    }

    /** 
     * The main application method 
     */
    public static void main(String[] args) {
        new ConversionBinDecServer();
    }
}
