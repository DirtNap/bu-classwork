package edu.bu.cs232;
import java.util.Scanner;
public class Assignment02 {
	
	private InputReader inputReader;

	public Assignment02(Scanner input) {
		this.inputReader = new InputReader(input);
	}
	protected void Main() {
		System.out.println(this.inputReader.readInteger("Enter an int"));
	}

	public static void main(String[] args) {
			Assignment02 self = new Assignment02(new Scanner(System.in));
			self.Main();
	}

}
