package gamelogic;

public class MineException extends Exception {
	
	@Override
	public String getMessage() {
		return "There are too many or too few mines on the board. The game can not start with this setting.";
	}
}
