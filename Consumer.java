import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
  This is the consumer class. Consumers request the values from the buffer.
*/
class Consumer implements Runnable {

    //The buffer used has a Linked List structure used as a queue
    private Buffer buffer;
    //To get the local host as well as the selected port number, and socket
    private InetAddress host;
    private final int PORT = 1234;
    private Socket socket;
    //To send requests to the server as well as receiving responses
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Consumer class that reads the buffer
     * @param buffer
     */
    public Consumer(Buffer buffer) {
        //buffer used is a Linked List used as a queue
        this.buffer = buffer;
        try {
            //to set up local server and in and out for sending and receiving requests
            host = InetAddress.getLocalHost();
            socket = new Socket(host, PORT);
            in = new BufferedReader(
                  new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(
                    socket.getOutputStream(), true);
        } catch(UnknownHostException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * run method, starts the consuming
     */
    public void run() {
        try{
            Random random = new Random();
            String request = "get";
            String response = "";
            while(true) {
                //Thread.sleep() was at the end of the while loop
                //We found it better at the beginning because not all consumers "spam" requests at the same time
                //The sleep is between 1 and 2 seconds
                Thread.sleep(random.nextInt(11) * 100 + 1000);
                //Request "get" from the local host
                out.println(request);
                //Wait for a response; the response would be the message requests
                response = in.readLine();
                System.out.println("Consumed: " + response);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
