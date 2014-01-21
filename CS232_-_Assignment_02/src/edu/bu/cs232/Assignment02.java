package edu.bu.cs232;
public class Assignment02 {
	protected static final String RUNNING_TOTAL_ROW = "You Entered:  %s.  Total: %f; Average: %f; Minimum: %f; Maximum: %f\n";
	
	private InputReader inputReader;

	public Assignment02(java.io.InputStream input) {
		this.inputReader = new InputReader(input);
	}
	protected int Main() {
		System.out.println(":: Running Total Reports ::\n" +
						   "===========================\n\n");
		String firstName = this.inputReader.readAlphaWord("Please enter your name (letters only)");
		String lastName = this.inputReader.readAlphaWord("Please enter your last name, one letter at a time.\n" +
														 "(Enter letters only; enter a blank line when done.)", true, true);
		System.out.printf("Hello %s %s.\n\n", firstName, lastName);
		while (true) {
			this.runReport(firstName, lastName);
			if (!this.inputReader.readBoolean("Continue with another report", "Yes", "No")) {
				break;
			}
		}
		System.out.printf("Thank you %s\n", firstName);
		return 0;
	}
	private void echoRow(RunningTotal.TotalRow row) {
		System.err.printf(Assignment02.RUNNING_TOTAL_ROW, row, row.sum, row.avg, row.min, row.max);
	}
	private void runReport(String firstName, String lastName) {
		String reportName;
		// Print uninitialized variable:
		// System.out.println(reportName);
		reportName = this.inputReader.readLine("What would you like to name this report?");
		int rowCount = this.inputReader.readInteger("How many numbers would you like in this report (minimum 7)", 7);
		RunningTotal table = new RunningTotal(rowCount);
		table.append(this.inputReader.readInteger("Enter a whole number"));
		this.echoRow(table.last());
		table.append(this.inputReader.readDouble("Enter a decimal number", true));
		this.echoRow(table.last());
		for (int i = 2; i < rowCount; ++i) {
			table.append(this.inputReader.readDouble("Enter a number"));
			this.echoRow(table.last());
		}
		TotalReport report = new TotalReport(String.format("%s %s", firstName, lastName), reportName, System.out);
		report.reportTotals(table);
	}
	public static void main(String[] args) {
			Assignment02 self = new Assignment02(System.in);
			System.exit(self.Main());
	}

}
