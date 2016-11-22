import lejos.hardware.motor.Motor;

public class pickUpPizza {
	
	public static double[] main(int pizza_side, int turn_90_angle);
	//assume robot is ready to begin picking up pizza
	// pizza_side: side of pizza, left == -1, right == 1
	// turn_90_angle: angle of motor rotation to turn the robot 90 degrees
	
	{
	int forwardmove = 180;
	int motorspeed = 200;
	int armup = 100;
	int armspeed = 150;
	
	//moving forward to pick up pizza
	leftMotor.speed = motorspeed
	rightMotor.speed = motorspeed
	leftMotor.rotate(forwardmove, true);
	rightMotor.rotate(forwardmove);
	
	//lifting pizza
	armMotor.speed = armspeed;
	armMotor.rotate = armup;
	
	//Turning to face forward
	if (pizza_side == 1); //turn right
	{
		leftMotor.rotateTo(turn_90_angle, true); 
		rightMotor.rotateTo(-turn_90_angle);
	}
	else //turn left
	{
		leftMotor.rotateTo(-turn_90_angle, true); 
		rightMotor.rotateTo(turn_90_angle);			
	}
	return;
	
	}
}
