package edu.bu.cs232.textbook.chapter2;
import java.util.Scanner;
public class Project04 {
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);
		int value = 0;
		while (value < 1000 || value >= 10000) {
			System.out.print("Enter a four digit integer:  ");
			value = input.nextInt();
		}
		int part = 10000;
		do {
			part /= 10;
			System.out.println(value / part);
			value = value % part;
		} while (part > 1);
	}
}
