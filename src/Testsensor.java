import lejos.hardware.Button;
	import lejos.hardware.lcd.*;
	import lejos.hardware.port.SensorPort;
	import lejos.hardware.sensor.EV3UltrasonicSensor;
	import lejos.utility.Delay;

public class Testsensor {
	
	public static void main(String[] args) {
	EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S3);
	LCD.clear();
	while (!Button.ENTER.isDown()) {
	int sampleSize = sonic.sampleSize();
	float[] sonicsample = new float[sampleSize];
	sonic.fetchSample(sonicsample, 0);
	LCD.clear();
	System.out.println(sonicsample[0]*100);
	Delay.msDelay(100);
	}
	sonic.close();
	}
	
}
