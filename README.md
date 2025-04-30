# COMPX234-A3
 In this assignment, you are going to develop a client/server networked system that  implements a “tuple space”.

Project Structure：
Client.java：The client program that reads a request file and sends operations to the server
Server.java：The main server program that listens on a port and manages client connections
ClientHandler.java：A thread that handles a single client’s requests
TupleSpace.java：A thread-safe shared space that stores and manages key-value pairs
StatisticsReporter.java：A background thread that prints server statistics every 10 seconds

Compilation Instructions：
Compile all Java files using UTF-8 encoding (required if using Windows `cmd`, which defaults to GBK):
javac -encoding UTF-8 *.java
（1）Start the Server
java Server 51234
！You can use any port between 50000–59999。
（2）Start the Client
java Client localhost 51234 requests.txt
