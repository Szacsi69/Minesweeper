package gamelogic;

import javax.swing.JComponent;

public class Counter extends Thread {
	private long startTime;
	private boolean count;
	
	private long hours;
	private long minutes;
	private long seconds;
	
	private JComponent display;
	
	/**
	 * A Counter létrejöttekor, inicializálja a startTime-ot az aktuális idõre,
	 * a count flaget pedig bebillenti, hogy elkezdõdhessen a számolás.
	 */
	public Counter() {
		startTime = System.currentTimeMillis();
		count = true;
	}
	
	/**
	 * Ha count értéke true, minden másodpercben kiszámolja az objektum létrejöttétõl eltelt idõt,
	 * és újrarajzolja a hozzárendelt JComponent-et.
	 * Az eltelt idõt másodpercben, percben és órában is eltárolja.
	 * Ha count értéke false, számlálás leáll, a metódus visszatér.
	 */
	@Override
	public void run() {
		try {
			while (count) {
				long actualTime = System.currentTimeMillis() - startTime;
				seconds = actualTime / 1000;
				minutes = seconds / 60;
				hours = minutes / 60;
				display.repaint();
				sleep(1000);
			}
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setDisplay(JComponent j) {
		display = j;
	}
	
	/**
	 * Leállítja a számlálást.
	 */
	public void stopCounting() {
		count = false;
	}
	
	public long getHours() {
		return hours;
	}
	
	public long getMinutes() {
		return minutes;
	}
	
	public long getSeconds() {
		return seconds;
	}
}