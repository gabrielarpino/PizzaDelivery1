import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;

public class followRoadToHouse {

	// Has to follow road to house, using PID control. Has to be smooth because
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

		LCD.clear();
		double correction;
		double integral = 0;
		double derivative;
		double lasterror = 0;
		double desired = 0.25;
		double Kp = 20000;
		double Ki = 250;
		double Kd = 100;
		int speed = 50;
		int angle = 10;

		int atHouse = 0;
		int currentHouse = 0;


		while (!atHouse) 
		{
			// Follow PID control algorithm

			int sampleSize = color.sampleSize();
			float[] redsample = new float[sampleSize];
			color.getRedMode().fetchSample(redsample, 0); //not sure if RedMode matters
			//added code
			double actual = redsample[0]; // not sure if sampleSize is the colour sensor value
			double error = desired - actual;
			integral = integral + error;
			derivative = error - lasterror;
			if (Button.UP.isDown())
			{
				integral = 0;
			}
			if (Button.DOWN.isDown())
			{
				Kp = 0;
			}
			if (Button.RIGHT.isDown())
			{
				Kp = Kp + 50;
			}
			if (Button.LEFT.isDown())
			{
				Kp = Kp - 50;
			}
			correction = Kp*error + Ki*integral+Kd*derivative; //negative
			turn(correction);
			//end added code
			LCD.clear();
			//System.out.println(redsample[0]);
			System.out.println(correction, speed, angle);
			System.out.println("Kp");
			System.out.println(Kp);
			lasterror = error;
			if (error*lasterror < 0)
			{
				integral = 0;
			}

			currentHouse = checkAtHouse();


		}
		color.close();
	}

	public static void turn(double direction, int speed, int angle) throws Exception 
	{		
		Motor.B.setSpeed(speed-direction);
		Motor.C.setSpeed(speed+direction);
		Motor.B.rotate(angle,true);
		Motor.C.rotate(angle,true);
	}

	public static void checkAtHouse(EV3UltrasonicSensor ultrasonic, int currentHouse){

		
		
	}
}