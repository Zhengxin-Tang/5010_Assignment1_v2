// @Time    : 2018/4/7 15:30
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/15 16:24
// @Instructions : This file is the connection thread on the server site once a connection is established between the sever
//                 and a client.

package com.bryan;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;
    private Dictionary dictionary;

    public ClientThread(Socket socket, Dictionary dictionary) {
        this.socket = socket;
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //To judge if exit when receive json contains "exit"
            Boolean ifEnd = false;
            try {
                while (true) {
                    //Received json message
                    String msg = in.readLine();
                    System.out.println("Received message:" + msg);

                    //The json object used for response
                    JSONObject objResponse = new JSONObject();

                    //Transfer String to json object
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(msg);
                    String command = (String) obj.get("command");
                    if (command.equals("Wrong command.")) {
                        objResponse.put("type","error");
                        objResponse.put("message","Wrong command.");
                    }
                    else if (!obj.containsKey("arguments")) {
                        objResponse.put("type","error");
                        objResponse.put("message","Unknown error.");
                    }
                    else {
                        JSONArray array = (JSONArray) obj.get("arguments");
                        //Command should be in "query","delete","add","update" or "exit"
                        switch (command)
                        {
                            case "query":
                                String Argument1 = (String) array.get(0);
                                if (dictionary.ifExist(Argument1)) {
                                    objResponse.put("type","response");
                                    objResponse.put("message",dictionary.getContent(Argument1));
                                }
                                //If queried word does not exits, response error
                                else {
                                    objResponse.put("type","error");
                                    objResponse.put("message","The word does not exist.");
                                }
                                break;
                            case "delete":
                                String Argument2 = (String) array.get(0);
                                if (dictionary.ifExist(Argument2)) {
                                    dictionary.deleteWord(Argument2);
                                    objResponse.put("type","response");
                                    objResponse.put("message","Delete successfully.");
                                }
                                //If queried word does not exits, response error
                                else {
                                    objResponse.put("type","error");
                                    objResponse.put("message","The word does not exist.");
                                }
                                break;
                            case "add":
                                String Argument3 = (String) array.get(0);
                                String Argument4 = (String) array.get(1);
                                if (!dictionary.ifExist(Argument3)) {
                                    dictionary.addWord(Argument3,Argument4);
                                    objResponse.put("type","response");
                                    objResponse.put("message","Add successfully.");
                                }
                                //If queried word already exits, response error
                                else {
                                    objResponse.put("type","error");
                                    objResponse.put("message","The word already exists.");
                                }
                                break;
                            case "update":
                                String Argument5 = (String) array.get(0);
                                String Argument6 = (String) array.get(1);
                                if (dictionary.ifExist(Argument5)) {
                                    dictionary.updateWord(Argument5,Argument6);
                                    objResponse.put("type","response");
                                    objResponse.put("message","Update successfully.");
                                }
                                //If queried word does not exits, response error
                                else {
                                    objResponse.put("type","error");
                                    objResponse.put("message","The word does not exist.");
                                }
                                break;
                            case "exit":
                                ifEnd = true;
                                objResponse.put("type","response");
                                objResponse.put("message","Server is ready for disconnect.");
                                break;
                            //If the command does not belong to anyone above, return "wrong command"
                            default:
                                objResponse.put("type","error");
                                objResponse.put("message","Wrong command.");
                        }
                    }

                    //Send response json object back to client
                    out.write((String.valueOf(objResponse)+"\n").getBytes("UTF-8"));
                    out.flush();

                    //If received message is "exit", then end the thread
                    if (ifEnd)
                        break;
                }
                in.close();
                out.close();
                socket.close();
                System.out.println("A connection is ended.");

            } catch (EOFException e) {
                if (socket != null)
                    socket.close();
                System.out.println("Unknown error: EOFException");
            } catch (ParseException e) {
                System.out.println("Unknown error: ParseException");
            }
        } catch (IOException e) {
            System.out.println("Unknown error: IOException");
            e.printStackTrace();
        }

    }

}
