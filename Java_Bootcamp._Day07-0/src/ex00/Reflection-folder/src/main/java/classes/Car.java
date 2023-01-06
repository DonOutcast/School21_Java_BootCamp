package classes;

public class Car {
	private String model;
	private Integer price;
	private Integer year;
	
	public Car() {}

	public Car(String model, Integer price, Integer year) {
		this.model = model;
		this.price = price;
		this.year = year;
	}

	public Integer computeAge() {
		return 2022 - this.year;
	}

	@Override
	public String toString() {
		return "Car [model=" + model + ", price=" + price + ", year=" + year + "]";
	}
	
}