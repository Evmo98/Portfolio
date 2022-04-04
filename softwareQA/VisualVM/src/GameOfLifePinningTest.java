import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	MainPanel panel;
	Cell[][] cells;

	@Before
	public void setUp() {
		panel = new MainPanel(5);
		cells = panel.getCells();
	}

	@Test
	public void TestIterateCell() {
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				cells[i][j] = Mockito.mock(Cell.class);
			}
		}
		Mockito.when(cells[2][1].getAlive()).thenReturn(true);
		Mockito.when(cells[2][2].getAlive()).thenReturn(true);
		Mockito.when(cells[2][3].getAlive()).thenReturn(true);
		assertFalse("Iterate cell did not return correctly", panel.iterateCell(2, 1));
	}

	@Test
	public void TestCalculateNextIteration() {
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				cells[i][j] = Mockito.mock(Cell.class);
			}
		}
		Mockito.when(cells[2][1].getAlive()).thenReturn(true);
		Mockito.when(cells[2][2].getAlive()).thenReturn(true);
		Mockito.when(cells[2][3].getAlive()).thenReturn(true);
		panel.calculateNextIteration();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				Mockito.verify(cells[i][j], Mockito.atLeastOnce()).getAlive();
			}
		}
	}

	@Test
	public void TestCellToString() {
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(i==2&&(j==1||j==2||j==3)) cells[i][j] = new Cell(true);
				else cells[i][j] = new Cell(false);
			}
		}
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(i==2&&(j==1||j==2||j==3)) assertEquals("X", cells[i][j].toString());
				else assertEquals(".", cells[i][j].toString());
			}
		}
	}
}
