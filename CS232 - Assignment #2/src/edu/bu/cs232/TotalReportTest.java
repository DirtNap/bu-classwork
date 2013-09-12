package edu.bu.cs232;
import java.io.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TotalReportTest {

	private RunningTotal runningTotal;
	private ByteArrayOutputStream byteStream;
	private PrintStream printStream;
	private String owner;
	private String name;

	@Before
	public void setUp() throws Exception {
		this.owner = new String("Michael Donnelly");
		this.name = new String("Test Report");
		this.runningTotal = new RunningTotal(7);
		for (int i = 1; i <= 7; ++i) {
			this.runningTotal.append(i);
		}
		this.byteStream = new ByteArrayOutputStream();
		this.printStream = new PrintStream(this.byteStream);
	}
	protected String runReport(TotalReport report) {
		report.reportTotals(this.runningTotal);
		return this.byteStream.toString();
	}
	@Test
	public void testTotalReport() {
		TotalReport tr = new TotalReport(this.owner, this.name, this.printStream);
		assertEquals(this.owner, tr.getOwner());
		assertEquals(this.name, tr.getName());
	}
	@Test
	public void testReportRows() {
		TotalReport tr = new TotalReport(this.owner, this.name, this.printStream);
		String testReport = this.runReport(tr);
		int lastSeen = -1;
		for (RunningTotal.TotalRow row : this.runningTotal) {
			String rowString = tr.getTableRow(row);
			int position = testReport.indexOf(rowString);
			assert position > lastSeen;
			lastSeen = position;
		}
	}

}
