import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class drivetopizza
{
	public drivetopizza(double[] pizzaCoords, int y_rotate_angle, int x_rotate_angle, int pizza_side, int turn_90_angle, double conversion_angle){
	// assume that the robot is facing forward at origin
	// pizzaCoords[x, y] contains x and y coordinates of robot on map
	// y_rotate_angle: angle to rotoate motors to drive to y coordinate of pizza
	// x_rotate_angle: angle to rotoate motors to drive to x coordinate of pizza
	// pizza_side: side of pizza, left == -1, right == 1
	// turn_90_angle: angle of motor rotation to turn the robot 90 degrees
	// conversion_angle: conversion from motor rotation angle to distance travelled by motor
	// returns robot coordinates and robot facing left or right
	{
		// Initiate Sensors/Motors
		static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
		static EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
		static EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S3);
		
		static NXTRegulatedMotor ultraSonicMotor = Motor.A;
		static NXTRegulatedMotor leftMotor = Motor.B;
		static NXTRegulatedMotor rightMotor = Motor.C;
		static NXTRegulatedMotor armMotor = Motor.D;
		
		int[] angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; //intial angle position of motors		
		rotatemotor(y_rotate_angle) // move to y coordinate of pizza
		pizzaCoords[1] += distance_motor_travlled(angle_intitial, conversion_angle); //update y coordinate
		turn_robot(pizza_side); // turn robot to face correct pizza
		angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; //intial angle position of motors	
		rotatemotor(x_rotate_angle)	//move to x coordinate of pizza
		pizzaCoords[0] += distance_motor_travlled(angle_intitial, conversion_angle); //update x coordinate
		return pizzaCoords;
	}
	
	public void turn_robot(int turn, int turn_90_angle);
	//turns robot given turn direction
	{
		if (turn == -1) //turn left
		{
			leftMotor.rotateTo(turn_90_angle, true); 
			rightMotor.rotateTo(-turn_90_angle);
		}
		else //turn right
		{
			leftMotor.rotateTo(-turn_90_angle, true); 
			rightMotor.rotateTo(turn_90_angle);			
		}
		return;
	}

	public double distance_motor_travlled(int[] angle_intitial, double conversion_angle);
	// calcuates the distance the robot has travelled
	{
		int[] angle_change = new int[2]; //change in angle after driving motors
		double average_angle_change; //average angle change between two motors
		double distance_travelled; //distance robot has travelled
		angle_change = [leftMotor.getTachoCount-angle_intitial[0], rightMotor.getTachoCount-angle_intitial[1]]; // calculate angle change
		average_angle_change = (double) (angle_change[0]+angle_change[1])/2; // average angle change between motors
		distance_travelled = average_angle_change*conversion_angle; // calculated travelled distance
		return distance_travelled;
	}

	public void rotatemotor(int angle);
	{
		leftMotor.rotateTo(angle, true); 
		rightMotor.rotateTo(angle);
		return;
	}
}
	
}