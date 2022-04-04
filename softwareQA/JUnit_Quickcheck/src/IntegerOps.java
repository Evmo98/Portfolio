public class IntegerOps {
	/**
	 * Computes the sum of x and y. On integer overflow, where the return value
	 * becomes larger than Integer.MAX_VALUE, 0 is returned instead.
	 * 
	 * @param x First integer
	 * @param y Second integer
	 * @return Sum of x and y, or 0 if integer overflow
	 */
	public static int add(int x, int y) {
		int sum = x + y;
		if (x > 0 && y > 0) if (sum < 0) return 0;
		if (x < 0 && y < 0) if (sum > 0) return 0;
		return sum;
	}

	/**
	 * Computes the different of x and y. On integer overflow, where the return
	 * value becomes larger than Integer.MAX_VALUE, 0 is returned instead.
	 * 
	 * @param x First integer
	 * @param y Second integer
	 * @return Difference between x and y, or 0 if integer overflow
	 */
	public static int subtract(int x, int y) {
		int diff = x - y;
		if (x > 0 && y < 0) if (diff < 0) return 0;
		if (x < 0 && y > 0) if (diff > 0) return 0;
		return diff;
	}
}
