// @Time    : 2018/4/7 14:15
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/10 12:29
// @Instructions : This file is the command line parser for the server site.

package com.bryan;

import org.kohsuke.args4j.Option;

public class CommandLineValuesServer {

    public CommandLineValuesServer(){

    }

    @Option(
            required = false,
            name = "-p",
            aliases = {"--port"},
            usage = "Port Address"
    )
    private int port = 4444;

    public int getPort() {
        return port;
    }

}
