package edu.bu.cs232;

public class Assignment04 extends Assignment03 {

	public Assignment04(InputReader reader) {
		super(reader);
	}

	@Override
	protected Shopper createShopper(int itemCount, double budget) {
		SortingShopper ss = new SortingShopper(itemCount, budget);
		return (Shopper) ss;
	}

	public static void main(String[] args) {
		Assignment04 self = new Assignment04(new InputReader(System.in, System.out));
		System.exit(self.Main());
	}

}
