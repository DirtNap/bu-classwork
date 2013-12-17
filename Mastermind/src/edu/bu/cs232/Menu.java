package edu.bu.cs232;

import java.io.PrintStream;

public class Menu {
	private MenuItems[] options;
	private String format;
	private InputReader reader;
	private PrintStream output;
	private String name;
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Menu(String menuName, PrintStream output, InputReader reader, MenuItems ... choices ) {
		this.options = choices;
		this.reader = reader;
		this.output = output;
		this.name = menuName;
		this.setFormat("%s\t%s%n");
	}
	public MenuItems getMenuOption() {
		this.printMenu();
		String selection = "";
		while (true) {
			selection = this.reader.readWord("Enter your choice");
			for (MenuItems mi : this.options) {
				if (mi.getSelector().equalsIgnoreCase(selection)) {
					return mi;
				}
			}
			this.output.println("Invalid Selection.");
		}
	}
	private void printMenu() {
		this.output.printf("%s%n%n", this.name);
		for (MenuItems mi : this.options) {
			System.out.printf(this.getFormat(), mi.getSelector(), mi.getDescriptor());
		}
		this.output.println();
	}
}
