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
	 * A Counter l�trej�ttekor, inicializ�lja a startTime-ot az aktu�lis id�re,
	 * a count flaget pedig bebillenti, hogy elkezd�dhessen a sz�mol�s.
	 */
	public Counter() {
		startTime = System.currentTimeMillis();
		count = true;
	}
	
	/**
	 * Ha count �rt�ke true, minden m�sodpercben kisz�molja az objektum l�trej�tt�t�l eltelt id�t,
	 * �s �jrarajzolja a hozz�rendelt JComponent-et.
	 * Az eltelt id�t m�sodpercben, percben �s �r�ban is elt�rolja.
	 * Ha count �rt�ke false, sz�ml�l�s le�ll, a met�dus visszat�r.
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
	 * Le�ll�tja a sz�ml�l�st.
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