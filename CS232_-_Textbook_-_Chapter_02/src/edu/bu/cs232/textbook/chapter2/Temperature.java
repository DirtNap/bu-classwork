package edu.bu.cs232.textbook.chapter2;

public class Temperature {
	private double temp;

	public Temperature(double Farenheit) {
		this.temp = Farenheit;
	}
	public void setFarenheit(double Farenheit) {
		this.temp = Farenheit;
	}
	public double getFarenheit() {
		return this.temp;
	}
	public void setCelcius(double Celcius) {
		this.temp = 9.0d / 5.0d * (this.temp + 32.0d);
	}
	public double getCelcius() {
		return 5.0d / 9.0d * (this.temp - 32.0d);
	}
}
