import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Buffer is brought in so client handler can use it, creates in and out for read requests,
 * sets up the client socket and the buffer used and methods of communication
 */
class ClientHandler implements Runnable {

    //The buffer is brought in so that the client handler may use it
    private Buffer buffer;
    private Socket client;
    //Creates in and out to read requests and send requests
    private BufferedReader in;
    private PrintWriter out;

    //sets up the client socket and the buffer used as well as the methods of communication
    public ClientHandler(Socket client, Buffer buffer) {
        this.client = client;
        this.buffer = buffer;
        try {
            in = new BufferedReader(
                  new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(
                    client.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /** The main place where values are added to the buffer and requested from the buffer
    * When the message is "get", the consumer is requesting a value from the buffer
    * Otherwise it's treated as a value wanting to be added to the buffer
     */
    public void run() {
        try {
            while(true) {
                String received = in.readLine();
                //Consumers .get()
                if(received.equals("get")) {
                    String message = buffer.get();
                    out.println(message);
                }
                //Producers .put()
                else {
                    buffer.put(received);
                    out.println(received);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
