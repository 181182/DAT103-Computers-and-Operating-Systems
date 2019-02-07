import java.util.concurrent.Semaphore;

//Denne klassen implementerer og lager bounded buffer ved å kjøre delt minne.

public class BoundedBuffer implements Buffer {
	//Maksimal størelse på bufferen.
	private static final int BUFFER_SIZE = 3; 
	//Antall gjenstander som er i bufferen
	private int count;
	//Hvor lang det er til neste frie posisjon i bufferen.
	private int in;
	//Hvor langt det er til første objekt som har verdi i buffer
	private int out;
	private Object[] buffer; // array of Objects
	private Semaphore mutex; // provides limited access to the buffer (mutual exclusion)
	private Semaphore empty; // keep track of the number of empty elements in the array
	private Semaphore full; // keep track of the number of filled elements in the array

	public BoundedBuffer() {
		// buffer is initially empty
		count = 0;
		in = 0;
		out = 0;
		buffer = new Object[BUFFER_SIZE];
		mutex = new Semaphore(1); // 1 for mutual exclusion
		empty = new Semaphore(BUFFER_SIZE); // array begins with all empty elements
		full = new Semaphore(0); // array begins with no elements
	}

	// producer calls this method
	public void insert(Object item) {
		/*
		 * while (count == BUFFER_SIZE){ // do nothing, if the buffer array cannot be
		 * used (because full) }
		 */
		try {
			empty.acquire(); // keep track of number of empty elements (value--)
			// This provides synchronization for the producer,
			// because this makes the producer stop running when buffer is full
			mutex.acquire(); // mutual exclusion
		} catch (InterruptedException e) {
			System.out.println("ERROR in insert(): " + e);
		}

		// add an item to the buffer
		++count;
		buffer[in] = item;
		// modulus (%) is the remainder of a division
		// for example, 0%3=0, 1%3=1, 2%3=2, 3%3=0, 4%3=1, 5%3=2
		in = (in + 1) % BUFFER_SIZE;

		// buffer information feedback
		if (count == BUFFER_SIZE) {
			System.out.println("BUFFER FULL " + "Producer inserted \"" + item + "\" count=" + count + ", " + "in=" + in
					+ ", out=" + out);
		} else {
			System.out
					.println("Producer inserted \"" + item + "\" count=" + count + ", " + "in=" + in + ", out=" + out);
		}

		mutex.release(); // mutual exclusion
		full.release(); // keep track of number of elements (value++)
		// If buffer was empty, then this wakes up the Consumer
	}

	// consumer calls this method
	public Object remove() {
		Object item = null;
		/*
		 * while (count == 0){ //if nothing in the buffer, then do nothing //the buffer
		 * array cannot be used (because empty) }
		 */

		try {
			full.acquire(); // keep track of number of elements (value--)
			// This provides synchronization for consumer,
			// because this makes the Consumer stop running when buffer is empty

			mutex.acquire(); // mutual exclusion
		} catch (InterruptedException e) {
			System.out.println("ERROR in try(): " + e);
		}

		// remove an item from the buffer
		--count;
		item = buffer[out];
		// modulus (%) is the remainder of a division
		// for example, 0%3=0, 1%3=1, 2%3=2, 3%3=0, 4%3=1, 5%3=2
		out = (out + 1) % BUFFER_SIZE;

		// buffer information feedback
		if (count == 0) {
			System.out.println("BUFFER EMPTY " + "Consumer removed \"" + item + "\" count=" + count + ", " + "in=" + in
					+ ", out=" + out);
		} else {
			System.out.println("Consumer removed \"" + item + "\" count=" + count + ", " + "in=" + in + ", out=" + out);
		}

		mutex.release(); // mutual exclusion
		empty.release(); // keep track of number of empty elements (value++)
		// if buffer was full, then this wakes up the Producer
		return item;
	}

}