import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Serverhandler class that uses the buffer linked list as a server. Sets up local host server
 */
class ServerHandler implements Runnable {

    //Buffer is a linked list used as a server
    private Buffer buffer;
    private Thread client;
    private Socket socket;
    private ServerSocket serverSocket;

    //Gets the local host server set up and the buffer used
    public ServerHandler(ServerSocket serverSocket, Buffer buffer) {
        this.serverSocket = serverSocket;
        this.buffer = buffer;
    }

    /**Both producers and consumers are seen as clients.
    *Listens to sockets that will want to be clients
    *Creates them as new threads
     */
    public void run() {
        try {
            while(true) {
                socket = serverSocket.accept();
                // System.out.println("New client accepted");
                client = new Thread(new ClientHandler(socket, buffer));
                client.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
