/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.io.*;
import java.util.Properties;
import model.BinDecModel;
import model.IncorrectNumberException;

/**
 * The main class of the server
 *
 * @author Gall Anonim
 * @version 1.2
 */
public class ConversionBinDecSeverTCP implements Closeable {

    /** port number. */
    private int PORT;
    /** field represents the socket waiting for client connections .*/
    private ServerSocket serverSocket;

    /**
     * Creates the server socket
     *
     * @throws IOException when port is already bind
     */
    private ConversionBinDecSeverTCP() throws IOException {
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
        serverSocket = new ServerSocket(PORT);
    }

    
    public static void main(String args[]) {

        try (ConversionBinDecSeverTCP tcpServer = new ConversionBinDecSeverTCP()) {
            System.out.println("Server started");
            while (true) {
                Socket socket = tcpServer.serverSocket.accept();
                try (SingleService singleService = new SingleService(socket)) {
                    singleService.realize();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}

/**
 * The server class servicing a single connection
 */
class SingleService implements Closeable {

    /**
     * socket representing connection to the client
     */
    private Socket socket;
    /**
     * buffered input character stream
     */
    private BufferedReader input;
    /**
     * Formatted output character stream
     */
    private PrintWriter output;
    
    private int first = 0;

    private BinDecModel model = new BinDecModel();
    /**
     * The constructor of instance of the SingleService class. Use the socket as
     * a parameter.
     *
     * @param socket socket representing connection to the client
     */
    public SingleService(Socket socket) throws IOException {
        this.socket = socket;
        output = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
    }

    /**
     * Realizes the service
     */
    public void realize() {
        try {
            output.println("Welcome to ConversionBinDec server");
            while (true) {
                String msg;
                StringBuffer sb = new StringBuffer();
                String rcvd = input.readLine();
                sb.append(rcvd);
                System.out.println(rcvd);
                if (sb.substring(0, 4).toUpperCase().equals("QUIT")) {
                    break;
                } else if (sb.substring(0, 4).toUpperCase().equals("HELP")) {
                    msg = "SEND : SEND value, CONV : CONV, HELP : HELP";
                    output.println(msg);
                } else if (sb.substring(0, 4).toUpperCase().equals("SEND")){
                    sb.delete(0, 5);
                    rcvd = sb.toString();
                    msg = "Got SEND command with number " + rcvd + " to convert";
                    output.println(msg);
                    try{
                        model.addValue(rcvd);
                    } catch (IncorrectNumberException ex){
                        output.println(ex.getMessage());
                    }
                } else if (sb.substring(0, 4).toUpperCase().equals("CONV")){
                    sb.delete(0, 5);
                    msg = "Got CONV command";
                    output.println(msg);
                    int numberOfElements = model.getValuesSize();
                    sb = new StringBuffer();
                    sb.append(numberOfElements);
                    msg = sb.toString();
                    output.println(msg);
                    for (int i = 0; i < numberOfElements; i++) {
                        if(rcvd.length() < 3) {
                            model.addConvertedValue(model.convertDecToBin(i));
                        }
                        else if(rcvd.charAt(0) == '0' && (rcvd.charAt(1) == 'b' || rcvd.charAt(1) == 'B')) {
                            model.addConvertedValue(model.convertBinToDec(i));
                        }
                        else {
                            model.addConvertedValue(model.convertDecToBin(i));
                        }
                        msg = model.getValue(i);
                        output.println(msg);
                        msg = model.getConvertedValue(i);
                        output.println(msg);
                    }
                    model.clearValues();
                }
                
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}