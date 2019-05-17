// @Time    : 2018/4/7 15:21
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/15 16:10
// @Instructions : This file is the client site. The command line should include the server's IP address and the port number
//                 which is 4444. The client can query, delete, add and update the dictionary on the server site.

package com.bryan;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        //command line option parsing for client
        CommandLineValues values = new CommandLineValues();
        CmdLineParser parser = new CmdLineParser(values);
        try {
            parser.parseArgument(args);
            //The port number should be 4444
            socket = new Socket(values.getHost(), values.getPort());
            System.out.println("Client Connected...");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner cmdin = new Scanner(System.in);
            while (true) {
                String msg = cmdin.nextLine();
                JSONObject obj = new JSONObject();
                //To judge if the command form is right
                int index = msg.indexOf(" ");
                if (index != -1) {
                    String command = msg.substring(0, index);
                    switch (command)
                    {
                        case "query":
                            //To judge if the command form is right
                            if (msg.contains("<") && (msg.indexOf("<") < msg.indexOf(">"))) {
                                //Package the json object
                                obj.put("command","query");
                                JSONArray list1 = new JSONArray();
                                list1.add(msg.substring(msg.indexOf("<")+1,msg.indexOf(">")));
                                obj.put("arguments",list1);
                                //Send the json object to server
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            else {
                                obj.put("command","Wrong command.");
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            break;
                        case "delete":
                            //To judge if the command form is right
                            if (msg.contains("<") && (msg.indexOf("<") < msg.indexOf(">"))) {
                                //Package the json object
                                obj.put("command","delete");
                                JSONArray list2 = new JSONArray();
                                list2.add(msg.substring(msg.indexOf("<")+1,msg.indexOf(">")));
                                obj.put("arguments",list2);
                                //Send the json object to server
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            else {
                                obj.put("command","Wrong command.");
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            break;
                        case "add":
                            //To judge if the command form is right
                            if (msg.contains("<") && (msg.indexOf("<") < msg.indexOf(">"))
                                    && (msg.indexOf(">") < msg.lastIndexOf("<"))
                                    && (msg.lastIndexOf("<") < msg.lastIndexOf(">"))){
                                //Package the json object
                                obj.put("command","add");
                                JSONArray list3 = new JSONArray();
                                list3.add(msg.substring(msg.indexOf("<")+1,msg.indexOf(">")));
                                list3.add(msg.substring(msg.lastIndexOf("<")+1,msg.lastIndexOf(">")));
                                obj.put("arguments",list3);
                                //Send the json object to server
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            else {
                                obj.put("command","Wrong command.");
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            break;
                        case "update":
                            //To judge if the command form is right
                            if (msg.contains("<") && (msg.indexOf("<") < msg.indexOf(">"))
                                    && (msg.indexOf(">") < msg.lastIndexOf("<"))
                                    && (msg.lastIndexOf("<") < msg.lastIndexOf(">"))) {
                                //Package the json object
                                obj.put("command","update");
                                JSONArray list4 = new JSONArray();
                                list4.add(msg.substring(msg.indexOf("<")+1,msg.indexOf(">")));
                                list4.add(msg.substring(msg.lastIndexOf("<")+1,msg.lastIndexOf(">")));
                                obj.put("arguments",list4);
                                //Send the json object to server
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            else {
                                obj.put("command","Wrong command.");
                                out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                                out.flush();
                            }
                            break;
                        //If the command does not belong to anyone above, then just send the json object in default form
                        default:
                            obj.put("command",command);
                            JSONArray list5 = new JSONArray();
                            list5.add(msg.substring(msg.indexOf(" ")));
                            obj.put("arguments",list5);
                            out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                            out.flush();
                    }
                }
                //If the command is "exit"
                else if (msg.equals("exit")) {
                    obj.put("command","exit");
                    JSONArray list6 = new JSONArray();
                    obj.put("arguments",list6);
                    out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                    out.flush();
                }
                else {
                    obj.put("command","Wrong command.");
                    out.write((String.valueOf(obj)+"\n").getBytes("UTF-8"));
                    out.flush();
                }

                //Receive the response from server
                String response = in.readLine();
                JSONParser parserResponse = new JSONParser();
                JSONObject objResponse = (JSONObject) parserResponse.parse(response);
                String message = (String) objResponse.get("message");

                //As "exit" is entered and is accepted by server, end the connection
                if(message.equals("Server is ready for disconnect."))
                    break;

                if (message.equals("Wrong command.")) {
                    System.out.println(response);
                }
                else if (message.equals("Unknown error.")) {
                    System.out.println(response);
                }
                else {
                    System.out.println(message);
                }

            }

            System.out.println("Disconnect from server.");
            cmdin.close();
            in.close();
            out.close();
            socket.close();

        } catch (UnknownHostException e) {
            System.out.println("Unknown error: UnknownHostException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unknown error: IOException");
            e.printStackTrace();
        } catch (ParseException p) {
            System.out.println("Unknown error: ParseException");
        }
        catch (CmdLineException var4) {
            System.err.println(var4.getMessage());
            parser.printUsage(System.err);
            System.exit(-1);
        }
    }
}

