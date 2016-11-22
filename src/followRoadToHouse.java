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
		static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
		static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
		ultrasonic.

		int sampleSize = color.sampleSize();
		float[] redsample = new float[sampleSize];
		color.getRedMode().fetchSample(redsample, 0); //not sure if RedMode matters

		LCD.clear();
		double correction;

		int currentHouse = 0;
		int atDesiredHouse = 0;
		int threshold = 50;

		while (!atDesiredHouse) 
		{
			// Run P control

			int sampleSize = color.sampleSize();
			float[] redsample = new float[sampleSize];
			color.getRedMode().fetchSample(redsample, 0); //not sure if RedMode matters
			//added code
			double desired = 0.25;
			double Kp = 20000;
			
			double actual = redsample[0]; // not sure if sampleSize is the colour sensor value
			double error = desired - actual;
			correction = error*Kp; //negative
			turn(correction);
			//end added code
			LCD.clear();
			//System.out.println(redsample[0]);
			System.out.println(correction);

			currentHouse = houseUpdate(ultrasonic, currentHouse, threshold)


		}
		color.close();
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

	public static void turn(double direction, int speed, int angle) throws Exception 
	{		
		Motor.B.setSpeed(speed-direction);
		Motor.C.setSpeed(speed+direction);
		Motor.B.rotate(angle,true);
		Motor.C.rotate(angle,true);
	}

	public static void houseUpdate(EV3UltrasonicSensor ultrasonic, int currentHouse, int threshold){





	}
}