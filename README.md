# 5010_Assignment1_v2
**1 Synopsis**
The assignment is to create an \online dictionary" application using client/server architecture. The system
consists of two main distributed components: server and clients, which may run on different hosts in the
network. Clients are Java programs which can connect to the server. The server is a Java application that
accepts multiple incoming TCP connections from clients and returns the meaning of a word stored in a
dictionary. In addition to seeking for the meaning of the words, the server allows clients to add, edit or
remove words. The dictionary data is shared among all the clients.
This assignment has been designed to use TCP Sockets. The server creates a pool of threads and uses
them to process clients requests.
**2 Dictionary Server**
The server maintains the dictionary. The dictionary contains the list of words and their meaning. The
server must serve multiple clients concurrently. Threads are used to achieve this functionality. The server
is also responsible for managing all the connected clients and responding to their requests. The server
listens for connection requests from clients. As soon as a client is connected to the server, the server is
ready to accept commands from the client. The command messages will allow the client to query, add,
update, and delete words in the dictionary. All the communicated messages between the server and clients
must be encapsulated in the form of JSON messages (more details to come).

- Use Java 1.8 or later.
- All message formats should be UTF-8 JSON encoded. Use a JSON library for this (e.g., jsonsimple
library).
- Your program should be cleanly finished by terminating all running threads.

- Package everything into a single runnable jar le, one for server, one for client.
- Your server and client should be executable exactly as follows:
```
java -jar server.jar [-p port]
java -jar client.jar -h hostname [-p port]
```
Square brackets ([]) indicate that what is inside the bracket is optional.
- Use command line option parsing (e.g. using the args4j library or your choice).
- The default server port should be 4444. A command line option [-p port] can override this.
- Pressing Ctrl-C should terminate either client or server.

