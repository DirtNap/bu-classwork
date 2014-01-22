package edu.bu.cs232;

import java.io.PrintStream;

public class TotalReport {
    protected static final String HEADER_FORMAT = "Name:\t%s\nOwner:\t%s\n\n"
            + "Input Number Highest Number Lowest Number Running Total Running Average\n"
            + "____________ ______________ _____________ _____________ _______________\n";
    protected static final String ROW_FORMAT = "%12s %14.2f %13.2f %13.2f %15.2f\n";
    protected static final String FOOTER_FORMAT = "\nGrand Total" + "\n___________"
            + "\n%11.2f\n\n";

    private String owner;
    private String name;
    private PrintStream output;

    public TotalReport(String owner, String name, PrintStream output) {
        this.owner = owner;
        this.name = name;
        this.output = output;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getTableRow(RunningTotal.TotalRow row) {
        return String.format(TotalReport.ROW_FORMAT, row, row.max, row.min, row.sum, row.avg);
    }

    public void reportTotals(RunningTotal table) {
        this.output.printf(TotalReport.HEADER_FORMAT, this.getName(), this.getOwner());
        for (RunningTotal.TotalRow row : table) {
            this.output.print(this.getTableRow(row));
        }
        this.output.printf(TotalReport.FOOTER_FORMAT, table.getTotal());
    }

    @Override
    public String toString() {
        return String.format("%s: A report for %s.", this.name, this.owner);
    }

}
