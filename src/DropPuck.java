import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;


public class DropPuck implements Behavior {
	
	private final NXTRegulatedMotor RIGHT_MOTOR;
	private final NXTRegulatedMotor LEFT_MOTOR;
	
	private final LightSensor RIGHT_EYE;
	private final LightSensor LEFT_EYE;

	private final int[][] POWER;
	
	private static double MAX_LIGHT;
	
	public DropPuck(NXTRegulatedMotor RM, NXTRegulatedMotor LM, int[][] P, double M_L, LightSensor RE, LightSensor LE) {

		RIGHT_MOTOR = RM;
		LEFT_MOTOR = LM;
		
		RIGHT_EYE = RE;
		LEFT_EYE = LE;
		
		POWER = P;
		
		MAX_LIGHT = M_L;
	}
	
	public boolean takeControl() {
		Driver.writeStatus("Drop Puck", "Take Control", "");
		return (RIGHT_EYE.getLightValue() + LEFT_EYE.getLightValue()) / 2 >= MAX_LIGHT;
	}

	
	public void action() {
		Driver.writeStatus("Drop Puck", "Action", "");
		RIGHT_MOTOR.setSpeed(POWER[2][0]);
		LEFT_MOTOR.setSpeed(POWER[2][1]);

		RIGHT_MOTOR.backward();
		LEFT_MOTOR.backward();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			
		}
	}

	public void suppress() {
		Driver.writeStatus("Drop Puck", "Suppress", "");
	}

}
