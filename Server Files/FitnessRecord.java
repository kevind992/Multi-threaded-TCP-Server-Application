//Author: Kevin Delassus - G00270791
import java.io.Serializable;

public class FitnessRecord implements Serializable{

	//Setting Variables
	private static final long serialVersionUID = 1L;
	private int id;
	private int recCount;
	private String date;
	private String mode;
	private String duration;

	//Constructors
	public FitnessRecord() {
		super();
	}

	public FitnessRecord(int id,int recCount, String date, String mode, String duration) {

		this.id = id;
		this.recCount = recCount;
		this.date = date;
		this.mode = mode;
		this.duration = duration;

	}
	//Getters and Setters
	public int getRecCount() {
		return recCount;
	}

	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
