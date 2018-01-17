//Author: Kevin Delassus - G00270791
import java.io.Serializable;

//MealRecord Class implements Serializable
public class MealRecord implements Serializable{

	//Setting Variables
	private static final long serialVersionUID = 1L;
	private int id;
	private int recCount;
	private String date;
	private String typeOfMeal;
	private String discription;
	
	//Constructors
	public MealRecord() {
		super();
	}
	public MealRecord(int id,int recCount, String date, String type, String dis) {
		
		this.id = id;
		this.recCount = recCount;
		this.date = date;
		this.typeOfMeal = type;
		this.discription = dis;
		
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
	public String getTypeOfMeal() {
		return typeOfMeal;
	}
	public void setTypeOfMeal(String typeOfMeal) {
		this.typeOfMeal = typeOfMeal;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
}
