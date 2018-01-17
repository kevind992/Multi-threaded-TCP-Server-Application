//Author: Kevin Delassus - G00270791
import java.io.Serializable;

//Member Class implements Serializable
public class Member implements Serializable{

	//Setting Variables
	private static final long serialVersionUID = 1L;
	private int id;
	private int recFitCount;
	private int recMealCount;
	private String name;
	private String password;
	private String address;
	private String ppsn;
	private String age;
	private String weight;
	private String height;

	
	//Constructors
	public Member() {
		super();
	}

	public Member(int id, int recFitCount,int recMealCount, String name, String password, String address, String ppsn, String age, String weight, String height) {

		this.id = id;
		this.recFitCount = recFitCount;
		this.recMealCount = recMealCount;
		this.name = name;
		this.password = password;
		this.address = address;
		this.ppsn = ppsn;
		this.age = age;
		this.weight = weight;
		this.height = height;

	}
	//Getters and Setters
	public int getRecFitCount() {
		return recFitCount;
	}

	public void setRecFitCount(int recFitCount) {
		this.recFitCount = recFitCount;
	}

	public int getRecMealCount() {
		return recMealCount;
	}

	public void setRecMealCount(int recMealCount) {
		this.recMealCount = recMealCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPpsn() {
		return ppsn;
	}

	public void setPpsn(String ppsn) {
		this.ppsn = ppsn;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
