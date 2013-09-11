package edu.bu.cs232;
import java.util.Scanner;
public class InputReader {
	protected Scanner inputSource;

	public InputReader(Scanner input) {
		this.inputSource = input;
	}
	public String readWord() {
		return this.inputSource.next();
	}
	public String readByCharacter(String terminator) {
		StringBuilder ret = new StringBuilder();
		
		return ret.toString();
	}
}
