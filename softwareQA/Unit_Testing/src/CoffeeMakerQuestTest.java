import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	Player player;
	Room room1;	// Small room
	Room room2;	// Funny room
	Room room3;	// Refinanced room
	Room room4;	// Dumb room
	Room room5;	// Bloodthirsty room
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		Config.setBuggyPlayer(true);
		Config.setBuggyRoom(true);
		
		// 1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		player = Mockito.mock(Player.class);
		cmq.setPlayer(player);
		// Player should not have any items (no coffee, no cream, no sugar)

		room1 = Mockito.mock(Room.class);
		room2 = Mockito.mock(Room.class);
		room3 = Mockito.mock(Room.class);
		room4 = Mockito.mock(Room.class);
		room5 = Mockito.mock(Room.class);
		room6 = Mockito.mock(Room.class);
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		Mockito.when(room1.getAdjective()).thenReturn("Small");
		Mockito.when(room2.getAdjective()).thenReturn("Funny");
		Mockito.when(room3.getAdjective()).thenReturn("Refinanced");
		Mockito.when(room4.getAdjective()).thenReturn("Dumb");
		Mockito.when(room5.getAdjective()).thenReturn("Bloodthirsty");
		Mockito.when(room6.getAdjective()).thenReturn("Rough");

		Mockito.when(room1.getFurnishing()).thenReturn("Quaint sofa");
		Mockito.when(room2.getFurnishing()).thenReturn("Sad record player");
		Mockito.when(room3.getFurnishing()).thenReturn("Tight pizza");
		Mockito.when(room4.getFurnishing()).thenReturn("Flat energy drink");
		Mockito.when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
		Mockito.when(room6.getFurnishing()).thenReturn("Perfect air hockey table");

		Mockito.when(room1.getItem()).thenReturn(Item.CREAM);
		Mockito.when(room2.getItem()).thenReturn(Item.NONE);
		Mockito.when(room3.getItem()).thenReturn(Item.COFFEE);
		Mockito.when(room4.getItem()).thenReturn(Item.NONE);
		Mockito.when(room5.getItem()).thenReturn(Item.NONE);
		Mockito.when(room6.getItem()).thenReturn(Item.SUGAR);
	}
	
	private void fillRooms() {
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
	}

	@After
	public void tearDown() {
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() {
		fillRooms();
		assertEquals(" INSTRUCTIONS (N,S,L,I,D,H) > ", cmq.getInstructionsString());
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() {
		fillRooms();
		Room myRoom = Mockito.mock(Room.class);
		assertEquals(false, cmq.addFirstRoom(myRoom));
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() {
		fillRooms();
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getAdjective()).thenReturn("Fake");
		Mockito.when(myRoom.getFurnishing()).thenReturn("Fake bed");
		Mockito.when(myRoom.getItem()).thenReturn(Item.NONE);
		assertEquals(true, cmq.addRoomAtNorth(myRoom, "North", "South"));
		Mockito.verify(room6, Mockito.atLeastOnce()).setNorthDoor("North");
		Mockito.verify(myRoom, Mockito.atLeastOnce()).setSouthDoor("South");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() {
		fillRooms();
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getAdjective()).thenReturn("Fake");
		Mockito.when(myRoom.getFurnishing()).thenReturn("Flat energy drink");
		Mockito.when(myRoom.getItem()).thenReturn(Item.NONE);
		assertEquals(false, cmq.addRoomAtNorth(myRoom, "North", "South"));
		Mockito.verify(room6, Mockito.never()).setNorthDoor("North");
		Mockito.verify(myRoom, Mockito.never()).setSouthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() {
		fillRooms();
		assertEquals(null, cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() {
		fillRooms();
		assertEquals(true, cmq.setCurrentRoom(room3));
		assertEquals(room3, cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player does not have any items.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n", cmq.processCommand("I"));
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() {
		fillRooms();
		cmq.setCurrentRoom(room1);
		assertEquals("There might be something here...\nYou found some creamy cream!\n", cmq.processCommand("l"));
		Mockito.verify(player, Mockito.atLeastOnce()).addItem(Item.CREAM);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandn() {
		fillRooms();
		cmq.setCurrentRoom(room4);
		assertEquals("", cmq.processCommand("n"));
		assertEquals(room5, cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandS() {
		fillRooms();
		cmq.setCurrentRoom(room1);
		assertEquals("A door in that direction does not exist.\n", cmq.processCommand("s"));
		assertEquals(room1, cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() {
		fillRooms();
		assertEquals(null + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n", cmq.processCommand("D"));
		assertEquals(true, cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() {
		fillRooms();
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(true);
		assertEquals(null + "\nYou drink the beverage and are ready to study!\nYou win!\n", cmq.processCommand("D"));
		assertEquals(true, cmq.isGameOver());
	}
		
	/**
	 * Test case for addRoomAtNorth() with no rooms in roomList.
	 */
	@Test
	public void testAddRoomAtNorthNoRooms() {
		assertFalse(cmq.addRoomAtNorth(room1, "northDoor", "southdoor"));
	}
	
	/**
	 * Test case for addRoomAtNorth() with null door strings.
	 */
	@Test
	public void testAddRoomAtNorthNullDoors() {
		cmq.addFirstRoom(room1);
		assertFalse(cmq.addRoomAtNorth(room2, null, null));
	}
	
	/**
	 * Test to travel south from room 2 to room 1 (happy path)
	 */
	@Test
	public void testMoveSouthFromRoom1ToRoom2S() {
		fillRooms();
		cmq.setCurrentRoom(room2);
		assertEquals("", cmq.processCommand("S"));
		assertEquals(room1, cmq.getCurrentRoom());
		
	}
	
	public void testMoveSouthFromRoom1ToRoom2s() {
		fillRooms();
		cmq.setCurrentRoom(room2);
		assertEquals("", cmq.processCommand("s"));
		assertEquals(room1, cmq.getCurrentRoom());
		
	}
	
	/**
	 * Test H command
	 */
	@Test
	public void testCommandH() {
		fillRooms();
		cmq.setCurrentRoom(room1);
		assertEquals("N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n", cmq.processCommand("H"));
	}
	
	/**
	 * Test bad Command 
	 */
	@Test
	public void testBadCommand() {
		fillRooms();
		cmq.setCurrentRoom(room1);
		assertEquals("What?\n", cmq.processCommand("stinky"));
	}
	
	@Test
	public void testProcessCommandN() {
		fillRooms();
		cmq.setCurrentRoom(room4);
		assertEquals("", cmq.processCommand("N"));
		assertEquals(room5, cmq.getCurrentRoom());
	}

	@Test
	public void testAdjectiveReader() {
		fillRooms();
		cmq.setCurrentRoom(room1);
		assertEquals("The current room is Small", cmq.getCurrentRoomDescription());
	}
}
