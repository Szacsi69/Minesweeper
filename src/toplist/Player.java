package toplist;

public class Player {
	private String name;
	private long seconds;
	private String difficulty;
	
	public Player() {}
	
	public Player(String name, long time, String difficulty) {
		this.name = name;
		seconds = time;
		this.difficulty = difficulty;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSeconds() {
		return seconds;
	}
	
	public void setSeconds(long s) {
		seconds = s;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * Visszadaja a Player idejét HH:MM:SS formátumban.
	 * 
	 * @return	A Player ideje HH:MM:SS formátumban.
	 */
	public String getTime() {
		long h = seconds / 3600;
		long m = (seconds / 60) % 60;
		long s = seconds % 60;
		String hours = Long.toString(h);
		String mins = Long.toString(m);
		String secs = Long.toString(s);
		
		StringBuilder sb_hours = new StringBuilder(hours);
		if (sb_hours.length() < 2) sb_hours.insert(0, "0");
		
		StringBuilder sb_mins = new StringBuilder(mins);
		if (sb_mins.length() < 2) sb_mins.insert(0, "0");
		
		StringBuilder sb_secs = new StringBuilder(secs);
		if (sb_secs.length() < 2) sb_secs.insert(0, "0");
		
		return sb_hours.toString() + ":" + sb_mins.toString() + ":" + sb_secs.toString();
	}
}
