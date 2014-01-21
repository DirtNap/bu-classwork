package edu.bu.cs232.textbook.chapter1;
import java.io.PrintStream;
import java.util.Scanner;
public class Project02 {

	/**
	 * @param args
	 */
	public static int addNumbers(int count, Scanner input, PrintStream output) {
		int result = 0;
		output.print(String.format("Enter %d whole numbers on a line:  ", count));
		for (int i = 0; i < count; ++i) {
			result += input.nextInt();
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println("Hello out there Michael Donnelly.");
		Scanner keyboard = new Scanner(System.in);
		System.out.println(String.format("The sum of those %d numbers is %d",
				3, Project02.addNumbers(3, keyboard, System.out)));
	}

}
