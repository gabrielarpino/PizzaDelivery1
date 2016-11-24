import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;

//Avg_rotation = 698 degrees/full rotation ==> 174.5 degrees/quarter rotation

public class turnToFaceHouse {
	public static void main(int side) {
		int turn = 200;
		Motor.C.setSpeed(240);
		Motor.B.setSpeed(240);

		if (side == 1) { // turn to the right
			Motor.B.rotate(-turn,true);
			Motor.C.rotate(turn);
		}
		else { //turn to the left
			Motor.B.rotate(turn,true);
			Motor.C.rotate(-turn);
		}
	}
}