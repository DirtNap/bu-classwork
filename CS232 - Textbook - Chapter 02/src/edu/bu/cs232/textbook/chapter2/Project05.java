package edu.bu.cs232.textbook.chapter2;

import java.util.Scanner;

public class Project05 {
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);
		int value = 0;
		while (value < 1000 || value >= 10000) {
			System.out.print("Enter a four digit integer:  ");
			value = input.nextInt();
		}
		String work = Integer.toString(value);
		for (int i = 0; i < work.length(); ++i) {
			System.out.println(work.charAt(i));
		}
	}
}
