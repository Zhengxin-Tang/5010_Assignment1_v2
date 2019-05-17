// @Time    : 2018/4/7 14:13
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/10 12:23
// @Instructions : This file is the command line parser for the client site.

package com.bryan;

import org.kohsuke.args4j.Option;

public class CommandLineValues {

    public CommandLineValues(){

    }

    @Option(
            required = true,
            name = "-h",
            aliases = {"--host"},
            usage = "Host Address"
    )
    private String host;

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

    public String getHost() {
        return host;
    }
}
