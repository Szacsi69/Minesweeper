package gamelogic;

public class CountMaster {

	private Counter counter;
	
	/**
	 * Létrehoz egy új Counter-t, eltárolja, majd visszatér vele.
	 * 
	 * @return A létrehozott Counter
	 */
	public Counter makeACounter() {
		counter = new Counter();
		return counter;
	}
	
	/**
	 * Leállítja az attribútumként tárolt Counter számlálását.
	 */
	public void stopCounter() {
		counter.stopCounting();
	}
	
	/**
	 * Elkéri a Counter-tõl az általa számolt eltelt idõt másodpercben.
	 * 
	 * @return A Counter által számolt eltelt másodpercek
	 */
	public long getTime() {
		return counter.getSeconds();
	}
}
