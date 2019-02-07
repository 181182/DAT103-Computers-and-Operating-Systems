import java.util.concurrent.Semaphore;

//Denne klassen lager en buffer og produsereren og brukeren tråder.
public class Main {
	public static void main(String args[]) {
		//Lager en buffer som skal deles og brukes av produseren og brukeren.
		Buffer sharedBuffer = new BoundedBuffer();
		Thread producerThread = new Thread(new Producer(sharedBuffer));
		Thread consumerThread = new Thread(new Consumer(sharedBuffer));
		//start kommandoen legger til minne for nye tråder og kjører run() metoden.
		producerThread.start();
		consumerThread.start();
	}
}
