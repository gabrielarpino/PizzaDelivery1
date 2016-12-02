import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class drivetoroad
{
	public static double[] main(double[] pizzaCoords, double[] targetcoord, double closeness, double conversion_angle, int turn_90_angle, double conversion_distance, double threshold, int threshold_rotate, int sample_rate);
	// assume that the robot is facing forward on the inside of the pizza to begin with
	// pizzaCoords[x, y] contains x and y coordinates of robot on map
	// targetcoord contains x and y coordinates of target position
	// closeness is the closeness we are willing to have the robot to a barrier
	// conversion_angle: conversion from motor rotation angle to distance travelled by motor
	// turn_90_angle: angle of motor rotation to turn the robot 90 degrees
	// conversion_distance is the conversion from distance sensor to actual distance
	// threshold: when driving sideways along a barrier, the threshold distance from a barrier such that it is okay to drive forward again
	// threshold_rotate: angle to rotate motors to get robot into the opening when driving sideways
	// sample rate: delay time of samples from sensors, in milliseconds
	// returns x and y coordinates of robot and robot facing forward
	{
		int turn; // turn direction, 1 == right, -1 == left
		double return_array = new double[2]; // array holder for function output
		double max_distance; //max distance the robot can travel sideways 
		while (pizzaCoords[1] < targetcoord[1]) // exits when target y coordinate is reached
		{

			pizzaCoords[1] = drive_fwd_close(pizzaCoords[1], closeness, conversion_angle, targetcoord[1], sample_rate); //drives forwards towards roadblock until robot reaches a certain closeness
				if (pizzaCoords[1] >= targetcoord[1]) //checks if robot is at target y coordinate
				{
					break;
				}
			return_array = take_distance_measurements(); //returns whether to turn left or right, and max distance in sensor units that can be travelled
			// return_array[turn direction, max distance that can be travelled]
			turn = (int) return_array[0];
			max_distance = return_array[1]*conversion_distance; // convert max distance to distance units
			turn_robot(turn, turn_90_angle); //turns robot in best direction
			pizzaCoords[0] = drive_sideways_close(pizzaCoords[0], closeness, conversion_angle, turn, max_distance, threshold, threshold_rotate, sample_rate); //drives sideways until robot reaches roadblock or can drive forward again
			turn = flip_turn(turn); // flips turn to return pose to face forward
			turn_robot(turn, turn_90_angle); //turn robot to face forward
		}
		// loop will end when robot is at target y coordinate and facing forward
		
		if (pizzaCoords[0]<targetcoord[0])
		{
			turn = 1; //if robot is on left side of target x coordinate, turn right 
			max_distance = targetcoord[0] - pizzaCoords[0];
		}
		else
		{
			turn = -1; //otherwise turn left because robot is on the right side of the target
			max_distance = pizzaCoords[0] - targetcoord[0];
		}
		turn_robot(turn, turn_90_angle); // turn robot
		pizzaCoords[0] = drive_sideways_close(pizzaCoords[0], closeness, conversion_angle, turn, max_distance, threshold*1000000, threshold_rotate, sample_rate); //drives sideways until robot reaches target x coordinate
		//pizzaCoords[0] = drive_sideways_destination(pizzaCoords[0], targetcoord[0], conversion_angle, turn);

		xcoordinate = Math.cos(pizzaCoords[0]);		// Gather the actual x and y position of robot
		ycoordinate = Math.sin(pizzaCoords[1]);

		turn = flip_turn(turn);
		turn_robot(turn, turn_90_angle); //turn to face forward at target x and y coordinate
		return pizzaCoords; 
	}
	
	public static double drive_fwd_close(double y_coord, double closeness, double conversion_angle, double targetcoord, int sample_rate);
	// drives robot until close to barrier or target y coordinate reached
	{
		int[] angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; //intial angle position of motors
		double distance; // distance of robot from barrier
		float[] sonicsample = new float[sampleSize]; // intialize array to hold distance data
		double distance = (double) sonic.fetchSample(sonicsample, 0); //fetch distance data from sensor
		motors(1);// start motors
		while (closeness < distance) // only exit once distance from barrier is within closeness or target coordinate is reached
		{
			Thread.sleep(sample_rate); // delay of 0.1 seconds
			y_coord += distance_motor_travlled(angle_intitial, conversion_angle)// add distance travlled
			if (y_coord >= targetcoord) //check if y coordinate is at target y coordinate
			{
				break; // exit loop if true
			}
			angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; // reset intial angle
			distance = (double) sonic.fetchSample(sonicsample, 0);//  fetch distance from barrier			
		}
		// stop motors once closeness to barrier or target coordinate is reached
		motors(0);//stop motors
		return y_coord;
	}

	public static double drive_sideways_close(double x_coord, double closeness, double conversion_angle, int turn, double max_distance, double threshold, int threshold_rotate, int sample_rate);
	//drives sideways until robot reaches roadblock or can drive forward again
	{
		int[] angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; //intial angle position of motors
		double distance_barrier; // distance of robot from barrier
		double distance_travelled = 0;
		ultraSonicMotor.rotateTo(turn*90); // turn sensor to face perpendicular to robot, in positive y direction
		float[] sonicsample = new float[sampleSize]; // intialize array to hold distance data
		double distance_barrier = (double) sonic.fetchSample(sonicsample, 0); //fetch distance data from sensor
		motors(1);// start motors
		while (threshold > distance_barrier) // only exit once distance from barrier is within closeness or target coordinate is reached
		{
 			Thread.sleep(sample_rate); // delay of 0.1 seconds
   			// fetch angle of motor and calculate angle change	
			distance_travelled += distance_motor_travlled(angle_intitial, conversion_angle); // convert angle change to a distance travelled to update y coodinate
			if (max_distance <= distance_travelled) //check if y coordinate is at target y coordinate
			{
				break; // exit loop if true
			}
			// reset intial angle
			angle_intitial = [leftMotor.getTachoCount, rightMotor.getTachoCount]; // reset intial angle
			distance_barrier = (double) sonic.fetchSample(sonicsample, 0);//  fetch distance from barrier		
		}
		// stop motors once closeness or target coordinate is reached
		motors(0);//stop motors
   		if (threshold <= distance_barrier) //if loop exit was due to threshold being reached, drive until robot can fit through the opening
   		{
 			leftMotor.rotateTo(threshold_rotate, true); 
			rightMotor.rotateTo(threshold_rotate);		  			
   		}
		distance_travelled += distance_motor_travlled(angle_intitial, conversion_angle); // convert angle change to a distance travelled to update y coodinate
		x_coord += turn*distance_travelled
		ultraSonicMotor.rotateTo(-90*turn); // turn sensor back to face parrallel as robot
		return x_coord;		
   	}

	public static double[] take_distance_measurements();
	 //returns the appropriate direction to turn the robot and the max distance that can be travelled
	{
		double turn;
		double max_distance;
		ultraSonicMotor.rotateTo(90); //rotate distance sensor to face left
		float[] sonicsample = new float[sampleSize]; // intialize array to hold distance data
		double distance_left = (double) sonic.fetchSample(sonicsample, 0); //determine open distance to robot's left
		ultraSonicMotor.rotateTo(-180); //rotate distance sensor to face right
		double distance_right = (double) sonic.fetchSample(sonicsample, 0); //determine open distance to robot's right
		ultraSonicMotor.rotateTo(90); //rotate distance sensor to face left
		if (distance_left>distance_right) // compares the open distance to the robot's left and right
		{
			turn = -1;
			max_distance = distance_left;
		}
		else
		{
			turn = 1;
			max_distance = distance_right;
		}

		return [turn, max_distance];
	}


	public static int flip_turn(int turn);
	// flips the turning direction given
	{
		if (turn == -1)
		{
			turn = 1;
		}
		else
		{
			turn = -1;
		}
		return turn;
	}

	public static void turn_robot(int turn, int turn_90_angle);
	//turns robot given turn direction
	{
		if (turn == -1); //turn left
		{
			leftMotor.rotateTo(turn_90_angle, true); 
			rightMotor.rotateTo(-turn_90_angle);
		}
		else //turn right
		{
			leftMotor.rotateTo(-turn_90_angle, true); 
			rightMotor.rotateTo(turn_90_angle);			
		}
		return
	}


	pubic static double distance_motor_travlled(int[] angle_intitial, double conversion_angle);
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

	pubic static void motors(int motion);
	// turns on or stops driving motors
	{
		if (motion == 0)
		{
			leftMotor.stop();//turn off left motor to stop
	   		rightMotor.stop();//turn off right motor to stop
		}
		else
		{
			leftMotor.forward(); //turn on left motor to drive forward
	   		rightMotor.forward(); // turn on right motor to drive forward		
		}		
		return;
	}
}
