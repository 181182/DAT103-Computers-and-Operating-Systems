import java.util.Date;

/**
 * This is the producer thread for the bounded buffer problem.
 */

public class Producer implements Runnable{
    
    private  Buffer buffer;
           
     public Producer(Buffer b) {
       buffer = b;
    }
 
     public void run(){
       Date message;
       while (true) {
          System.out.println("Producer napping");
          SleepUtilities.nap();
       // produce an item & enter it into the buffer
          message = new Date();      
          System.out.println("Producer produced \"" + message + "\"");
          buffer.insert(message);
       }
    }
 }
