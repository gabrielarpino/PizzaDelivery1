import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class PizzaDelivery {
	private static final double START[] = {0, 0, 0};

	// Initiate Sensors/Motors
	static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
	static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
	static EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S3);
	
	static NXTRegulatedMotor ultraSonicMotor = Motor.A;
	static NXTRegulatedMotor leftMotor = Motor.B;
	static NXTRegulatedMotor rightMotor = Motor.C;
	static NXTRegulatedMotor armMotor = Motor.D;
	
	// Define input variables
	private double pathCoords[];
	private double pizzaCoords[];
	private int pizza_side;
	private int street;
	private int side;
	private int house_num;
	private double currentPose[];

	private void gatherinfo() { // Assigned to Rob
		// INT TO STRING CONVERSION:
		// -1 = left; 0 = center; 1 = right;
		// 1,2,3 represents house number
		get_info info = new get_info();
		pizza_side = info.get_pizza_loc(); //-1 or 1
		street = info.get_street(); // -1, 0, or 1
		side = info.get_side(); //-1 or 1
		house_num = info.get_house_num(); //1, 2, 3
	}
	
	private void driveToLocation(double[] pizzaCoords, int y_rotate_angle, int x_rotate_angle, int pizza_side, int turn_90_angle, double conversion_angle) { // Assigned to James
		drivetopizza driver = new drivetopizza(pizzaCoords, y_rotate_angle, x_rotate_angle,pizza_side, turn_90_angle, conversion_angle);
	}

	private void pickuppizza() { // Assigned to Sean
		pickUpPizza pickup = new pickUpPizza();
	}

	private void drivetoroad() {	// Assigned to James
		driveToRoad roaddriver = new driveToRoad();
	}

	private void followroadtohouse(house_num) {	// Assigned to Gabe
		followRoadToHouse roadfollower = new followRoadToHouse(house_num);
	}
	
	private void turntofacehouse(side) {	// Assigned to Rob
		turnToFaceHouse housefacer = new turnToFaceHouse(side);
	}
	
	private void dropoffpizza() {	// Assigned to Sean
		dropOffPizza laypizza = new dropOffPizza();
	}

	private void drivetostart() {
		driveToStart startdriver = new driveToStart();
	}

	private void deliver() {
		pizzaCoords = driveToLocation([0, 0],  90,  90,  -1,  90,  16.88/360);
		pickuppizza();
		pizzaCoords = drivetoroad.main(pizzaCoords, pathCoords, double closeness, double conversion_angle, int turn_90_angle, double conversion_distance, double threshold, int threshold_rotate, int sample_rate);
		followroadtohouse();
		turntofacehouse(side);
		dropoffpizza();
		drivetostart();
	}

	public static void main(String[] args) {
		//gatherinfo();
		//delivery.deliver();
		driveToLocation([0, 0],  90,  90,  -1,  90,  16.88/360);
	}
}