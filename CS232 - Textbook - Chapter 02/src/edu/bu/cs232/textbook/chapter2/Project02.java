package edu.bu.cs232.textbook.chapter2;
import java.util.Scanner;

public class Project02 {
	private Scanner input;
	private int size;
	private static final String TEMPLATE = "%s\t%d\n";
	public Project02(Scanner input, int size) {
		this.input = input;
		this.size = size;
	}
	public void stringDemo() {
		this.stringDemo(" ");
	}
	public void stringDemo(String delimiter) {
		System.out.printf("Enter %d string%s:  ", this.size, (this.size == 1?"":"s"));
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < this.size; ++i ) {
			String s = this.input.next();
			System.out.printf(Project02.TEMPLATE, s, s.length());
			if (result.length() > 0) {
				result.append(delimiter);
			}
			result.append(s);
		}
		System.out.printf(Project02.TEMPLATE, result, result.length());
	}
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);
		Project02 self = new Project02(input, 2);
		self.stringDemo();
	}
}
