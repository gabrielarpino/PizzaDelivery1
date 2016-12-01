import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;

public class followRoadToHouse {

	// Has to follow road to house, using P control. Has to be smooth because
	// the ultrasonic sensor has to be able to count houses.

	// Reference to Sensors/Motors
	
	// static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
	// static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
	// static EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S3);
	// static NXTRegulatedMotor ultraSonicMotor = Motor.A;
	// static NXTRegulatedMotor leftMotor = Motor.B;
	// static NXTRegulatedMotor rightMotor = Motor.C;
	// static NXTRegulatedMotor armMotor = Motor.D;
	
	public void followRoadToHouse(int desiredHouse) { 

		// Define input sensors
		static EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S1);
		static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
		public float[] sonicsample;


		LCD.clear();
		double correction;

		int currentHouse = 0;
		int atDesiredHouse = 0;
		int sonicthreshold = 50;

		//Rotate ultrasonic to point at houses, ultrasonic motor is motor A

		if (pizza_side == -1) {

			Motor.A.setSpeed(20);// slow
			Motor.A.rotate(-90,true);
		}
		else{
			Motor.A.setSpeed(20);// slow
			Motor.A.rotate(90,true);
		}


		while (!atDesiredHouse) 
		{
			// Get Ultrasonic sensor readings

			int sonic_sampleSize = sonic.sampleSize();
			sonicsample = new float[sonic_sampleSize];
			sonic.fetchSample(sonicsample, 0);

			// Get color sensor readings

			int sampleSize = color.sampleSize();
			float[] redsample = new float[sampleSize];
			color.getRedMode().fetchSample(redsample, 0); //not sure if RedMode matters

			//Run P control
			
			double desired = 0.25;
			double Kp = 20000;			
			double actual = redsample[0]; // not sure if sampleSize is the colour sensor value
			double error = desired - actual;
			correction = error*Kp; //negative
			turn(correction);
			LCD.clear();
			//System.out.println(redsample[0]);
			System.out.println(correction);

			// Check if at House

			if (sonicsample[0] < threshold) {		// Case where the ultrasonic sensor detects a house

				if (currentHouse == desiredHouse){	// Check if we have arrived at the desired house

					atDesiredHouse = 1;

				}
				else{
					currentHouse = currentHouse + 1;	// If not at desired house, increment the current house number
				}

			}


		}
		color.close();

		// Rotate robot to face correct house
		int turn_90_angle = 185; // This comes from our experimental turn calibration
		turn_robot(pizza_side, turn_90_angle); // turn robot to face correct pizza

		// Move robot forward
		Motor.B.rotate(90,true);
		Motor.C.rotate(90);

		// Robot should now be located in front of the house

	}

	public static void turn(double direction) throws Exception {		
		if (direction > 0) {
			int speed = (int) direction;
			Motor.B.setSpeed(0);
			Motor.C.setSpeed(speed); // one wheel turns faster
		}
		else if (direction < 0) {
			int speed = (int) direction*(-1);			
			Motor.B.setSpeed(speed);
			Motor.C.setSpeed(0);
		}
		else {
			Motor.B.setSpeed(70);
			Motor.C.setSpeed(70);
		}
		Motor.B.rotate(5,true);
		Motor.C.rotate(5,true);
			Motor.B.setSpeed(10);
			Motor.C.setSpeed(10);
	}


	public void turn_robot(int turn, int turn_90_angle)
	{
		//turns robot given turn direction		
		if (turn == -1) //turn left
		{
			Motor.B.rotate(turn_90_angle, true); 
			Motor.C.rotate(-turn_90_angle);
		}
		else //turn right
		{			
			Motor.B.rotate(-turn_90_angle, true); 
			Motor.C.rotate(turn_90_angle);			
		}
	}

	public double distance_motor_travlled(int[] angle_intitial, double conversion_angle)
	{
		// calcuates the distance the robot has travelled		
		int[] angle_change = new int[2]; //change in angle after driving motors
		double average_angle_change; //average angle change between two motors
		double distance_travelled; //distance robot has travelled
		angle_change[0] = Motor.B.getTachoCount()-angle_intitial[0];
		angle_change[1] = Motor.C.getTachoCount()-angle_intitial[1]; // calculate angle change
		average_angle_change = (double) (angle_change[0]+angle_change[1])/2; // average angle change between motors
		distance_travelled = average_angle_change*conversion_angle; // calculated travelled distance
		return distance_travelled;
	}

}