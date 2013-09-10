package edu.bu.cs232.textbook.chapter2;
import java.util.Scanner;

public class Project01 {
	private Scanner input;
	public Project01(Scanner input) {
		this.input = input;
	}
	public double doAverage(int count) {
		double ret = 0.0d;
		for (int i = 0; i < count; ++i) {
			System.out.print("Enter a number:  ");
			ret += this.input.nextDouble();
		}
		return ret / count;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		Project01 self = new Project01(input);
		System.out.printf("The average was %.2f", self.doAverage(3));
	}

}
