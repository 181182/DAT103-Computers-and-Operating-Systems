
public class SleepUtilities {
	private static final int NAP_TIME = 5; // max nap time in seconds

	/**
	 * Nap between zero and NAP_TIME seconds.
	 */
	public static void nap() {
		nap(NAP_TIME);
	}

	public static void nap(int duration) {
		int sleeptime = (int) (NAP_TIME * Math.random());
		System.out.println("Nap for " + sleeptime + " seconds");
		// Causes the currently executing thread to sleep (cease execution)
		// for the specified number of milliseconds,
		// subject to the precision and accuracy of system timers and schedulers.
		try {
			Thread.sleep(sleeptime * 1000);
		} catch (InterruptedException e) {
			// method sleep() throws InterruptedException - if any thread has interrupted
			// the current thread.
			System.out.println("ERROR in nap(): " + e);
		}
	}
}
