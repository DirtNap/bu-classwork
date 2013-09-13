package edu.bu.cs232;
import java.util.Scanner;
public class Assignment02 {
	
	private InputReader inputReader;

	public Assignment02(Scanner input) {
		this.inputReader = new InputReader(input);
	}
	protected void Main() {
		System.out.println("M1ke333!D".replaceAll("[^A-Za-z]", ""));
	}

	public static void main(String[] args) {
			Assignment02 self = new Assignment02(new Scanner(System.in));
			self.Main();
	}

}
