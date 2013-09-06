package edu.bu.cs232.textbook.chapter1;
import java.util.Scanner;
public class Exercise09 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.print("How old will you turn this year?  ");
		int currentAge = input.nextInt();
		BirthdayWizard bw = new BirthdayWizard(currentAge);
		System.out.print("Enter an age to find a year:  ");
		int targetAge = input.nextInt();
		if (targetAge < 0) {
			System.out.println("Invalid age!");
		} else {
			System.out.println(String.format("You will be %d in the year %d!", targetAge, 
					bw.getYearByAge(targetAge)));
		}
	}

}
