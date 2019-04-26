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
 * Class that sets up the producer structure.
 */
class Producer implements Runnable {

    //The buffer used has a Linked List structure used as a queue
    private Buffer buffer;
    //To get the local host as well as the selected port number, and socket
    private InetAddress host;
    private final int PORT = 1234;
    private Socket socket;
    //To send requests to the server as well as receiving responses
    private BufferedReader in;
    private PrintWriter out;

    public Producer(Buffer buffer) {
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
     * Producer run method. starts the producing
     */
    public void run() {
        try {
            int i = 0;
            Random random = new Random();
            String message = "";
            String response = "";
            while(true) {
                //Thread.sleep() was at the end of the while loop
                //We found it better at the beginning because not all producers "spam" messages at the same time
                //The sleep is between 1 and 2 seconds
                Thread.sleep(random.nextInt(11) * 100 + 1000);
                //Message is "packet 0", "packet 1", "packet 2", etc...
                message = "Packet " + i;
                //Send a request to put this in the buffer
                out.println(message);
                //Wait for a response; the response would be the message sent
                response = in.readLine();
                System.out.println("Produced: " + response);
                i++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
