import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class get_info {

	public int get_pizza_loc() {
	// Pizza pick-up (left (-1) or right (1))
		int pizza_loc;
		while (Button.LEFT.isUp() && Button.RIGHT.isUp()){ 
		}
		if (Button.LEFT.isDown()) {
			pizza_loc = -1;
			System.out.println("Pick up pizza on my left.");
		}
		else {
			pizza_loc = 1;
			System.out.println("Pick up pizza on my right.");
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
			System.out.println("Deliver to street on the left.");
		}
		else if (Button.ENTER.isDown()) {
			street = 0;
			System.out.println("Deliver to street in the center.");
		}
		else {
			street = 1;
			System.out.println("Deliver to street on the right.");
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
			System.out.println("Deliver to left side of the street.");
		}
		else {
			pizza_loc = 1;
			System.out.println("Deliver to right side of the street.");
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
			System.out.println("Deliver to first house.");
		}
		else if (Button.ENTER.isDown()) {
			house_num = 2;
			System.out.println("Deliver to second house.");
		}
		else {
			house_num = 3;
			System.out.println("Deliver to third house.");
		}
		while (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown()){
		}
		return house_num;
	}

}