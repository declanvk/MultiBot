import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;


public class DropPuck implements Behavior {
	
	private static NXTRegulatedMotor RIGHT_MOTOR;
	private static NXTRegulatedMotor LEFT_MOTOR;
	
	private static LightSensor RIGHT_EYE;
	private static LightSensor LEFT_EYE;

	private static int[][] POWER;
	
	private static double MAX_LIGHT;
	
	public DropPuck(NXTRegulatedMotor RM, NXTRegulatedMotor LM, int[][] P, double M_L) {

		RIGHT_MOTOR = RM;
		LEFT_MOTOR = LM;
		
		POWER = P;
		
		MAX_LIGHT = M_L;
	}
	
	public boolean takeControl() {
		return (RIGHT_EYE.getLightValue() + LEFT_EYE.getLightValue()) / 2 >= MAX_LIGHT;
	}

	
	public void action() {
		RIGHT_MOTOR.setSpeed(POWER[2][2]);
		LEFT_MOTOR.setSpeed(POWER[2][2]);

		RIGHT_MOTOR.backward();
		LEFT_MOTOR.backward();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			
		}
	}

	public void suppress() {
		
	}

}
