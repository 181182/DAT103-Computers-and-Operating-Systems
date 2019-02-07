import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilo {
	static int philosophersNumber = 5;
	static Philosopher philosophers[] = new Philosopher[philosophersNumber];
	static gaffel gaffels[] = new gaffel[philosophersNumber];

	static class gaffel {

		public Semaphore mutex = new Semaphore(1);

		void grab() {
			try {
				mutex.acquire();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		void release() {
			mutex.release();
		}

		boolean isFree() {
			return mutex.availablePermits() > 0;
		}

	}

	static class Philosopher extends Thread {

		public int number;
		public gaffel leftgaffel;
		public gaffel rightgaffel;

		Philosopher(int num, gaffel left, gaffel right) {
			number = num;
			leftgaffel = left;
			rightgaffel = right;
		}

		public void run() {
			System.out.println("Hei! Jeg er philosopher #" + number);

			while (true) {
				leftgaffel.grab();
				System.out.println("Philosopher #" + number + " tar venstre gaffel.");
				rightgaffel.grab();
				System.out.println("Philosopher #" + number + " tar hoyre gaffel.");
				eat();
				leftgaffel.release();
				System.out.println("Philosopher #" + number + " slipper venstre gaffel.");
				rightgaffel.release();
				System.out.println("Philosopher #" + number + " slipper hoyre gaffel.");
			}
		}

		void eat() {
			try {
				int sleepTime = ThreadLocalRandom.current().nextInt(0, 1000);
				System.out.println("Philosopher #" + number + " spiser for " + sleepTime);
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

	}

	public static void main(String args[]) {
		System.out.println("Dining philosophers problem.");

		for (int i = 0; i < philosophersNumber; i++) {
			gaffels[i] = new gaffel();
		}

		for (int i = 0; i < philosophersNumber; i++) {
			philosophers[i] = new Philosopher(i, gaffels[i], gaffels[(i + 1) % philosophersNumber]);
			philosophers[i].start();
		}

		while (true) {
			try {
				Thread.sleep(1000);
				boolean deadlock = true;
				for (gaffel f : gaffels) {
					if (f.isFree()) {
						deadlock = false;
						break;
					}
				}
				if (deadlock) {
					Thread.sleep(1000);
					System.out.println("--Deadlock--");
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

	}

}
