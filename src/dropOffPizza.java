import lejos.hardware.motor.Motor;

public class dropOffPizza {
	
	public static double[] main(void);
	{
		int armdown = -150;
		int armspeed = 150;
		
		
		//dropping pizza
		Motor.D.setSpeed(armspeed);
		Motor.D.rotate(armdown,true);
}

}