import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	Player player;
	boolean isDrinky = false;
	boolean isCoffeeDrank = false;
	ArrayList<Room> roomList = new ArrayList<Room>(); //IMPORTANT: 0 = Southernmost Room
	int currRoom;
	
	CoffeeMakerQuestImpl() {
		// Constructor
		currRoom = -1;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		return isDrinky;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		if(roomList.size() > 0 || room == null){
			return false;
		} else {
			roomList.add(room);
			return true;
		}
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		// Check if the room list is empty
		if(roomList.isEmpty()){
			return false;
		}
		// Test if the north and south door values entered are null
		if(northDoor == null || southDoor == null){
			return false;
		}
		// Searches the room list and checks if the adjectives are unique
		for(int i = 0; i < roomList.size(); i++){
			Room cur = roomList.get(i);
			if(room.getAdjective().equals(cur.getAdjective()) || room.getFurnishing().equals(cur.getFurnishing())){
				return false;
			}

		}
		// Once all tests are passed, set the last room to the new one and return true
		Room lastRoom = roomList.get(roomList.size()-1);
		lastRoom.setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		room.setNorthDoor(null);
		roomList.add(room);
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		if(currRoom < 0){
			return null;
		} else {
			return roomList.get(currRoom);
		}
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		for(int i = 0; i < roomList.size(); i++){
			Room checkRoom = roomList.get(i);
			if(room.getAdjective().equals(checkRoom.getAdjective())){
				currRoom = i;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		if (cmd.equals("N") || cmd.equals("n")) {
			if(currRoom >= roomList.size() - 1) return ("A door in that direction does not exist.\n\n" + roomList.get(currRoom).getDescription());
			currRoom+=1;
			return ("");
		}
		else if (cmd.equals("S") || cmd.equals("s")) {
			if(currRoom <= 0) return ("A door in that direction does not exist.\n");
			currRoom-=1;
			return ("");
		}
		else if (cmd.equals("L") || cmd.equals("l")) {
			switch(roomList.get(currRoom).getItem()) {
				case COFFEE:
					player.addItem(Item.COFFEE);
					return ("There might be something here...\nYou found some caffeinated coffee!\n");
				case CREAM:
					player.addItem(Item.CREAM);
					return ("There might be something here...\nYou found some creamy cream!\n");
				case SUGAR:
					player.addItem(Item.SUGAR);
					return ("There might be something here...\nYou found some sweet sugar!\n");
				case NONE:
					return ("You don't see anything out of the ordinary.\n");
			}
		}
		else if (cmd.equals("I") || cmd.equals("i")) {
			return (player.getInventoryString());
		}
		else if (cmd.equals("D") || cmd.equals("d")) {
			isDrinky = true;
			if(player.checkCoffee() && player.checkCream() && player.checkSugar()){
				return (player.getInventoryString() + "\nYou drink the beverage and are ready to study!\nYou win!\n");
			} else if(player.checkCoffee()){
				return (player.getInventoryString() + "\nWithout cream, you get an ulcer and] cannot study.\nYou lose!\n");
			} else {
				return (player.getInventoryString() + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n");
			}
		}
		else if (cmd.equals("H") || cmd.equals("h")) {
			return "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
		}
		return "What?\n";
	}

	//Private method that prints to the player what the adjective of the current room is
	public String getCurrentRoomDescription(){
		if(currRoom < 0){
			return null;
		} else {
			Room cur = roomList.get(currRoom);
			return "The current room is " + cur.getAdjective();
		}
	}
	
}

