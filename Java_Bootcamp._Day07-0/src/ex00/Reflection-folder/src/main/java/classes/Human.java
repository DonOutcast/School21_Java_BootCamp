package classes;

public class Human {
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer age;
	private Integer height;
	
	public Human() {}

	public Human(String firstName, String middleName, String lastName, Integer age, Integer height) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.age = age;
		this.height = height;
	}

	public String getFullName() {
		return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
	}

	@Override
	public String toString() {
		return "Human [age=" + age + ", firstName=" + firstName + ", height=" + height + ", lastName=" + lastName
				+ ", middleName=" + middleName + "]";
	}
}