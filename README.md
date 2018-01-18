Ryan Ho and Scott Hicks

First, compile the ftps.c and ftpc.c files by entering the command 

<code>make</code>

Then, start the server with the command

<code>ftps \<local-port\></code>

Then start the client with the command

<code>ftpc \<remote-IP\> \<remote-port\> \<local-file-to-transfer\></code>

Here are some sample commands for starting the client and server. For this example, the server is running on OSU's beta server and the client is running on OSU's gamma server.

<code>ftps 35000</code>

<code>ftpc 164.107.113.18 35000 pic.jpeg</code>

