package edu.bu.cs232.textbook.chapter1;

import java.util.Calendar;


public class BirthdayWizard {
	private int birthYear;
	

	public BirthdayWizard(int startAge) {
		this.birthYear = Calendar.getInstance().get(Calendar.YEAR) - startAge;
	}
	public int getYearByAge(int targetAge) {
		return this.birthYear + targetAge;
	}
}
