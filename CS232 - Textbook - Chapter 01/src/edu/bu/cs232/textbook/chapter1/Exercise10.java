package edu.bu.cs232.textbook.chapter1;
import java.util.Scanner;
public class Exercise10 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Enter two numbers:  ");
		Scanner input = new Scanner(System.in);
		int startAt = input.nextInt();
		int endAt = input.nextInt();
		if (endAt <= startAt || startAt < 0) {
			System.out.println("Invalid numbers!");
		} else {
			System.out.println(String.format("There are %d integers between %d and %d.",
					endAt - startAt +1, startAt, endAt));
		}
	}

}
