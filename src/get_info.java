import lejos.hardware.Button;

public class get_info {

	public int get_pizza_loc() {
	// Pizza pick-up (left (-1) or right (1))
		int pizza_loc;
		while (Button.LEFT.isUp() && Button.RIGHT.isUp()){ 
		}
		if (Button.LEFT.isDown()) {
			pizza_loc = -1;
			System.out.println("Pick-up on left.");
		}
		else {
			pizza_loc = 1;
			System.out.println("Pick-up on right.");
		}
		while (Button.LEFT.isDown() || Button.RIGHT.isDown()){
		}
		return pizza_loc;
	}
	
	public int get_street() {
	// Pick a street (left (-1), centre (0), or right (1))
		int street;
		while (Button.LEFT.isUp() && Button.RIGHT.isUp() && Button.ENTER.isUp()){
		}
		if (Button.LEFT.isDown()) {
			street = -1;
			System.out.println("Left Street.");
		}
		else if (Button.ENTER.isDown()) {
			street = 0;
			System.out.println("Center Street.");
		}
		else {
			street = 1;
			System.out.println("Right Street.");
		}
		while (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown()){
		}
		return street;
	}

	public int get_side(){
	// Pick a side of the street (left (-1) or right (1))
		int side;
		while (Button.LEFT.isUp() && Button.RIGHT.isUp()){ 
		}
		if (Button.LEFT.isDown()) {
			side = -1;
			System.out.println("Left side.");
		}
		else {
			side = 1;
			System.out.println("Right side.");
		}
		while (Button.LEFT.isDown() || Button.RIGHT.isDown()){
		}
		return side;
	}

	public int get_house_num(){
	// Pick a house number (1, 2, or 3)
		int house_num;
		while (Button.LEFT.isUp() && Button.RIGHT.isUp() && Button.ENTER.isUp()){
		}
		if (Button.LEFT.isDown()) {
			house_num = 1;
			System.out.println("First house.");
		}
		else if (Button.ENTER.isDown()) {
			house_num = 2;
			System.out.println("Second house.");
		}
		else {
			house_num = 3;
			System.out.println("Third house.");
		}
		while (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown()){
		}
		while (Button.ENTER.isUp()){
		}
		return house_num;
	}

}