package edu.bu.cs232.textbook.chapter1;
import java.util.Scanner;
public class Exercise16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Enter a number greater than 1:  ");
		Scanner input = new Scanner(System.in);
		int target = input.nextInt();
		if (target > 1) {
			int count = 0;
			double val = (target);
			while (val >= 2.0) {
				val /= 2.0;
				++count;
			}
			System.out.println(count);
		} else {
			System.out.println("Invalid number!");
		}
	}

}
