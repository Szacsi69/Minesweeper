package gamelogic;

public class CountMaster {

	private Counter counter;
	
	/**
	 * L�trehoz egy �j Counter-t, elt�rolja, majd visszat�r vele.
	 * 
	 * @return A l�trehozott Counter
	 */
	public Counter makeACounter() {
		counter = new Counter();
		return counter;
	}
	
	/**
	 * Le�ll�tja az attrib�tumk�nt t�rolt Counter sz�ml�l�s�t.
	 */
	public void stopCounter() {
		counter.stopCounting();
	}
	
	/**
	 * Elk�ri a Counter-t�l az �ltala sz�molt eltelt id�t m�sodpercben.
	 * 
	 * @return A Counter �ltal sz�molt eltelt m�sodpercek
	 */
	public long getTime() {
		return counter.getSeconds();
	}
}
