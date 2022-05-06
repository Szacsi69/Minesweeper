package gamelogic;

public class UnknownDifficultyException extends Exception {
	
	@Override
	public String getMessage() {
		return "This is an unknown diffictultytype. The game can not start with this setting";
	}

}
