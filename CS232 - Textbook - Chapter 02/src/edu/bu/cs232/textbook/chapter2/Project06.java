package edu.bu.cs232.textbook.chapter2;
import java.util.Scanner;
public class Project06 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a temperature in Farenheit (whole degrees only):  ");
		int value = input.nextInt();
		Temperature temp = new Temperature((double)value);
		System.out.printf("%d degrees Farenheit is %.1f degrees Celcius.", value, temp.getCelcius());
	}

}
