package edu.bu.cs232.textbook.chapter1;
import java.util.Scanner;
public class Exercise08 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("How old are you?  ");
		Scanner input = new Scanner(System.in);
		int age = input.nextInt();
		System.out.println(String.format("You are %d years old.", age));
	}

}
