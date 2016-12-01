import java.util.Arrays;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
//import lejos.hardware.motor.EV3RegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;

public class drivetopizza
{
	
	static EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S3);
	
	public double[] get_pizza_cords(double[] pizzaCoords, int y_rotate_angle, int x_rotate_angle, int pizza_side, int turn_90_angle, double conversion_angle)
	{
	// assume that the robot is facing forward at origin
	// pizzaCoords[x, y] contains x and y coordinates of robot on map
	// y_rotate_angle: angle to rotoate motors to drive to y coordinate of pizza
	// x_rotate_angle: angle to rotoate motors to drive to x coordinate of pizza
	// pizza_side: side of pizza, left == -1, right == 1
	// turn_90_angle: angle of motor rotation to turn the robot 90 degrees
	// conversion_angle: conversion from motor rotation angle to distance travelled by motor
	// returns robot coordinates and robot facing left or right
		// Initiate Sensors/Motors
//		static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
//		static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
//		static EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S3);
		Motor.B.setSpeed(100);
		Motor.C.setSpeed(100);		
		Motor.D.setSpeed(50);	

		// Initialize the gyro sensor
		float[] gyro_sample;
		gyro.getAngleMode(); 		// Set to angle mode
		int gyro_sample_size = gyro.sampleSize(); 
		gyro_sample = new float[gyro_sample_size];

		// Calibrate gyro sensor
		gyro_cal();

		int[] angle_intitial = new int[2];
		angle_intitial[0] = Motor.B.getTachoCount(); //initial angle position of motors	
		angle_intitial[1] = Motor.C.getTachoCount();
		rotatemotor(y_rotate_angle); // move to y coordinate of pizza
		pizzaCoords[1] += distance_motor_travlled(angle_intitial, conversion_angle); //update y coordinate
		turn_robot(pizza_side, turn_90_angle); // turn robot to face correct pizza

		angle_intitial[0] = Motor.B.getTachoCount();
		angle_intitial[1] = Motor.C.getTachoCount(); //intial angle position of motors	
		rotatemotor(x_rotate_angle);	//move to x coordinate of pizza
		pizzaCoords[0] += distance_motor_travlled(angle_intitial, conversion_angle); //update x coordinate
		Motor.D.rotate(-90);
		turn_robot(-pizza_side, turn_90_angle); // turn robot to face correct pizza		
		return pizzaCoords;
	}
	
	public void turn_robot(int turn, int turn_90_angle)
	{
		
		float[] gyro_sample;
		gyro.getAngleMode(); 		// Set to angle mode
		int gyro_sample_size = gyro.sampleSize(); 
		gyro_sample = new float[gyro_sample_size];


		//turns robot given turn direction		
		if (turn == -1) //turn left
		{
			gyro.getAngleMode().fetchSample(gyro_sample,0);
			while ( (gyro_sample[0] % 360) != 90){
				gyro.getAngleMode().fetchSample(gyro_sample,0);
				int rotation = (int)(0.5*(gyro_sample[0] % 360));
				Motor.B.rotate(rotation, true); // Multiply rotation by a proportionality constant proportional to gyro, essentially P controller for gyro
				Motor.C.rotate(-rotation);

			}
		}
		else //turn right
		{			
			gyro.getAngleMode().fetchSample(gyro_sample,0);
			while ( (gyro_sample[0] % 360) != -90){
				gyro.getAngleMode().fetchSample(gyro_sample,0);
				int rotation = (int)(0.5*(gyro_sample[0] % 360));
				Motor.B.rotate(rotation, true); 
				Motor.C.rotate(-rotation);

			}			
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

	public void rotatemotor(int angle)
	{
		Motor.B.rotate(angle, true); 
		Motor.C.rotate(angle);
	}

	public void gyro_cal() {
		
		float[] gyro_sample;
		gyro.getAngleMode(); 		// Set to angle mode
		int gyro_sample_size = gyro.sampleSize(); 
		gyro_sample = new float[gyro_sample_size];
		
		System.out.println("Hold for gyro calibration");
		Delay.msDelay(500);
		gyro.reset(); 					// Reset the gyro

		// Wait for gyro to finish calibrating
		// will output NaN until calibration complete
		while ((gyro_sample[0] % 360) == Float.NaN){
			Delay.msDelay(40);
		}

		System.out.println("Gyro calibration complete");
	}
}
	
