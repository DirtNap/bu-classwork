package edu.bu.cs232;

import java.util.Iterator;

public class RunningTotal implements Iterable<RunningTotal.TotalRow>, Iterator<RunningTotal.TotalRow> {
	// Read-only class representing a single row
	public class TotalRow {
		public final double inputNumber;
		public final double sum;
		public final double avg;
		public final double min;
		public final double max;
		protected final String fmt;
		public TotalRow(double inputNumber, double sum, double avg, double min, double max, String fmt) {
			this.inputNumber = inputNumber;
			this.sum = sum;
			this.avg = avg;
			this.min = min;
			this.max = max;
			this.fmt = fmt;
		}
		public String toString() {
			return String.format(this.fmt, this.inputNumber);
		}
	}
	// Format constants
	protected static final String INTEGER_FORMAT = "%.0f";
	
	protected static final String DECIMAL_FORMAT = "%.2f";
	// Internal representation
	private double [] inputNumbers;
	private double [] sums;
	private double [] avgs;
	private double [] mins;
	private double [] maxs;
	private String [] fmts;
	protected int currentIndex;
	
	private int currentIterIndex;
	
	// Constructor
	public RunningTotal(int size) {
		this.inputNumbers = new double[size];
		this.sums = new double[size];
		this.avgs = new double[size];
		this.mins = new double[size];
		this.maxs = new double[size];
		this.fmts = new String[size];
		this.currentIndex = -1;
		this.currentIterIndex = -1;
	}

	// Methods for adding various types of numbers.  Internal "format" argument
	//  tracks how the value should be represented.
	public void append(double value) {
		this.append(value, RunningTotal.DECIMAL_FORMAT);
	}
	private void append(double value, String format) {
		++this.currentIndex;
		this.inputNumbers[this.currentIndex] = value;
		this.fmts[this.currentIndex] = format;
		if (this.currentIndex == 0) {
			this.sums[this.currentIndex] = value;
			this.maxs[this.currentIndex] = value;
			this.mins[this.currentIndex] = value;
		} else {
			int prev = this.currentIndex - 1;
			this.sums[this.currentIndex] = this.sums[prev] + value;
			if (this.mins[prev] < value) {
				this.mins[this.currentIndex] = this.mins[prev];
			} else {
				this.mins[this.currentIndex] = value;
			}
			if (this.maxs[prev] > value) {
				this.maxs[this.currentIndex] = this.maxs[prev];
			} else {
				this.maxs[this.currentIndex] = value;
			}
		}
		this.avgs[this.currentIndex] = this.sums[this.currentIndex] / this.length();
	}
	public void append(long value) {
		this.append((double)value, RunningTotal.INTEGER_FORMAT);
	}

	// Methods for retrieving individual rows.
	public TotalRow get() {
		return this.get(this.currentIndex);
	}
	public TotalRow get(int index) {
		if (index > this.currentIndex || index < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return new TotalRow(this.inputNumbers[index], this.sums[index], this.avgs[index],
				this.mins[index], this.maxs[index], this.fmts[index]);
	}

	public double getAverage() {
		return this.getAverage(this.currentIndex);
	}
	public double getAverage(int index) {
		return this.getByIndex(index, this.avgs);
	}
	// Methods for finding the current value of an aggregate, or the value at a particular point.
	private double getByIndex(int index, double[] member) {
		if (index >= 0) {
			return member[index];
		} else {
			return 0;
		}
	}
	public double getMax() {
		return this.getMax(this.currentIndex);
	}
	public double getMax(int index) {
		return this.getByIndex(index, this.maxs);
	}
	public double getMin() {
		return this.getMin(this.currentIndex);
	}
	public double getMin(int index) {
		return this.getByIndex(index, this.mins);
	}
	public double getTotal() {
		return this.getTotal(this.currentIndex);
	}
	public double getTotal(int index) {
		return this.getByIndex(index, this.sums);
	}

	// Iterable Interfaces
	@Override
	public boolean hasNext() {
		return this.currentIterIndex < this.currentIndex;
	}
	@Override
	public Iterator<RunningTotal.TotalRow> iterator() {
		this.currentIterIndex = -1;
		return this;
	}
	@Override
	public TotalRow next() {
		return this.get(++this.currentIterIndex);
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove method not implemented");
	}


	// Utility methods
	public TotalRow last() {
		return this.get();
	}
	public int length() {
		return this.currentIndex + 1;
	}
	public String toString() {
		return String.format(RunningTotal.DECIMAL_FORMAT, this.getTotal());
	}
	
}
