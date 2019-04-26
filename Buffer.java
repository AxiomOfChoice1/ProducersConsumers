import java.util.LinkedList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Buffer class. Defines the size of our buffer based on user input
 */
class Buffer {

    //The buffer and it's size
    private LinkedList<String> buffer;
    private int sizeOfBuffer;

    //The serversocket along with the selected port number
    private ServerSocket serverSocket;
    private static final int PORT = 1234;
    //The thread handler is the one that listens for new producers and consumers clients
    private Thread serverHandler;

    public Buffer(int sizeOfBuffer) {
        //The linked list is used as the buffer
        this.sizeOfBuffer = sizeOfBuffer;
        buffer = new LinkedList<String>();
        try {
            //set up the serversocket
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            System.out.println("Unable to set up a server");
            e.printStackTrace();
        }
        //Set up the server handler (as a new thread)
        serverHandler = new Thread(new ServerHandler(serverSocket, this));
        serverHandler.start();
    }

    /**This is for the producers. buffer.put("10") adds "10" to the buffer
     * @param packet
     */
    public synchronized void put(String packet) {
        //While the buffer is full, wait()
        while(buffer.size() == sizeOfBuffer) {
            try {
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Adds the content to the Linked List
        buffer.add(packet);
        //Notify that the list isn't empty anymore
        notifyAll();
    }

    /**
     * Synchronization method! Very important. Gets the first added value if producer, if consumer checks if buffer is full
     * @return packet
     */
    //For consumers: buffer.get() returns the values as FIFO basis
    public synchronized String get() {
        //While the buffer is empty, wait() because you can't get anything
        while(buffer.size() == 0) {
            try {
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Get the first added value in the linked list
        String packet = buffer.removeFirst();
        //notify that the buffer is not full, and return the content
        notifyAll();
        return packet;
    }

}
