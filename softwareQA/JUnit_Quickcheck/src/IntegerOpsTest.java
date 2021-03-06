import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(JUnitQuickcheck.class)
public class IntegerOpsTest {

	/**
	 * Property to test: If x >= 0 and y >= 0,
	 *                   the return value of IntegerOps.add(x, y) is >= 0. 
	 * 
	 * @param x First integer generated by QuickCheck
	 * @param y Second integer generated by QuickCheck
	 */
	@Property(trials = 1000)
	public void testAdd(int x, int y) {
		// System.out.println("testAdd x='" + x + "', y='" + y + "'");
		if (x >= 0 && y >= 0) {
			assertTrue(IntegerOps.add(x, y) >= 0);
		}
		
	}
	
	/**
	 * Property to test: If x >= y,
	 *                   the return value of IntegerOps.subtract(x, y) is >= 0. 
	 * 
	 * @param x First integer generated by QuickCheck
	 * @param y Second integer generated by QuickCheck
	 */
	@Property(trials = 1000)
	public void testSubtract(int x, int y) {
		// System.out.println("testSubtract x='" + x + "', y='" + y + "'");
		if (x >= y) {
			assertTrue(IntegerOps.subtract(x, y) >= 0);
		}
	}

}
