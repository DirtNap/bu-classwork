package edu.bu.cs232.textbook.chapter1;
import java.util.Scanner;
public class Exercise17 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int max = Integer.MIN_VALUE;
		int current;
		System.out.print("Enter a list of integers (enter anythin else to quit):  ");
		Scanner input = new Scanner(System.in);
		while (true) {
			try {
				current = input.nextInt();
				if (current > max) {
					max = current;
				}
			} catch (java.util.InputMismatchException ex) {
				break;
			}
		}
		System.out.println(String.format("%d is the largest number in the list.", max));
	}

}
