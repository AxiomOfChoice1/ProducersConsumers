import java.util.Scanner;

/**
 * Producer Consumer class.
 */
public class ProducersConsumers {

    /**
     * main method for producer/consumer. Asks for user input and runs the producer consumer
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //The number of producers that will exist
        System.out.println("Enter the number of producers: ");
        int numberOfProducers = sc.nextInt();
        //Number of consumers
        System.out.println("Enter the number of consumers: ");
        int numberOfConsumers = sc.nextInt();
        //The maximum size of the buffer
        System.out.println("Enter the size of the buffer: ");
        int sizeOfBuffer = sc.nextInt();
        Buffer buffer = new Buffer(sizeOfBuffer);

        //Declare the number of Producers and Consumers that will exist
        Thread[] producers = new Thread[numberOfProducers];
        Thread[] consumers = new Thread[numberOfConsumers];

        System.out.println("Number of producers are " + numberOfProducers + ".");
        System.out.println("Number of consumers are " + numberOfConsumers + ".");
        System.out.println("The size of the buffer is " + sizeOfBuffer + ".");

        //Initiate and start each Producer and Consumer
        for(int i = 0; i < numberOfProducers; i++){
            producers[i] = new Thread(new Producer(buffer));
            producers[i].start();
        }
        for(int i = 0; i < numberOfConsumers; i++){
            consumers[i] = new Thread(new Consumer(buffer));
            consumers[i].start();
        }

    }

}
