// @Time    : 2018/4/7 15:24
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/15 16:12
// @Instructions : This file is the server site. It read command lines and establish a TCP connection by port 4444 and
//                 listen to any connection on the network. The server supports multi-connection.

package com.bryan;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        //Command line option parsing for server
        CommandLineValuesServer values = new CommandLineValuesServer();
        CmdLineParser parser = new CmdLineParser(values);
        try {
            parser.parseArgument(args);
            //Server is listening on port 4444
            serverSocket = new ServerSocket(values.getPort());
            System.out.println("Server is listening...");

            Dictionary dictionary = new Dictionary();
            while (true) {
                //Server waits for a new connection
                Socket socket = serverSocket.accept();
                // Java creates new socket object for each connection.
                System.out.println("A client is connected.");

                // A new thread is created per client
                Thread client = new Thread(new ClientThread(socket,dictionary));
                // It starts running the thread by calling run() method
                client.start();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CmdLineException var4) {
            System.err.println(var4.getMessage());
            parser.printUsage(System.err);
            System.exit(-1);
        } finally {
            if (serverSocket != null)
                serverSocket.close();
        }
    }

}
