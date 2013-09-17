package edu.bu.cs232;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RunningTotalTest {
	
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;

	private RunningTotal runningTotal;

	@Before
	public void setUp() throws Exception {
		this.runningTotal = new RunningTotal(50);
	}

	@Test
	public void testAggregation() {
		this.runningTotal.append(3);
		this.runningTotal.append(4);
		this.runningTotal.append(5);
		assertEquals(3, this.runningTotal.length());
		assertEquals(12.0d, this.runningTotal.getTotal(), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(4.0d, this.runningTotal.getAverage(), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(3.0d, this.runningTotal.getMin(), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(5.0d, this.runningTotal.getMax(), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(7.0d, this.runningTotal.getTotal(1), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(3.5d, this.runningTotal.getAverage(1), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(3.0d, this.runningTotal.getMin(1), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		assertEquals(4.0d, this.runningTotal.getMax(1), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testAllocation() {
		for (int i = 0; i <= 51; ++i) {
			this.runningTotal.append(i);
		}
	}

	@Test
	public void testAppendDouble() {
		this.runningTotal.append(3.14f);
		this.runningTotal.append(1e+129);
	}
	
	@Test
	public void testAppendLong() {
		byte b = 1;
		this.runningTotal.append(b);
		this.runningTotal.append(3000000000L);
	}
	@Test
	public void testGet() {
		double x = 123.789d;
		this.runningTotal.append(x);
		RunningTotal.TotalRow tr = this.runningTotal.get(0);
		Assert.assertEquals(x, tr.inputNumber, RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
	}
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testGetOutOfBounds() {
		this.runningTotal.append(6);
		this.runningTotal.get(this.runningTotal.length());
	}
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testGetUninitialized() {
		// Aggregates should be 0 when uninitialized...
		assertEquals(0.0d, this.runningTotal.getTotal(), RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		// ...but rows should be unavailable.
		this.runningTotal.get(0);
	}
	@Test
	public void testIteration() {
		double [] vals = { 1.0d, 3.14d, 100.0d, 90.3d };
		for (double val : vals) {
			this.runningTotal.append(val);
		}
		int index = 0;
		for (RunningTotal.TotalRow row : this.runningTotal) {
			assertEquals(vals[index++], row.inputNumber, RunningTotalTest.ALLOWABLE_FLOAT_VARIANCE);
		}
	}
	@Test
	public void testLength() {
		assertEquals(0, this.runningTotal.length());
		int ct = 0;
		this.runningTotal.append(++ct);
		this.runningTotal.append(++ct);
		assertEquals(ct, this.runningTotal.length());
	}
}
