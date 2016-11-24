import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;

public class driveToStart {
	public static double[] main(double[] pizzaCoords, double[] targetcoord, double closeness, double conversion_angle, int turn_90_angle, double conversion_distance, double threshold, int threshold_rotate, int sample_rate)
	{
		//reversing the target coordinates
		targetcoord[0] = -1*targetcoord[0];
		targetcoord[1] = -1*targetcoord[1];
		targetcoord[2] = -1*targetcoord[2];
		//should use the gyro to start pointing perfectly backwards
		driveToRoad();
	}
}