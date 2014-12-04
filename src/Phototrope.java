import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;

public class Phototrope implements Behavior {
	private static NXTRegulatedMotor RIGHT_MOTOR;
	private static NXTRegulatedMotor LEFT_MOTOR;
	
	private static LightSensor RIGHT_EYE;
	private static LightSensor LEFT_EYE;

	private static int[][] POWER;

	private static TouchSensor T_S;
	
	public Phototrope(NXTRegulatedMotor RM, NXTRegulatedMotor LM, LightSensor RE, LightSensor LE, int[][] P, TouchSensor TS) {
		RE.setFloodlight(false);
		LE.setFloodlight(false);

		RIGHT_MOTOR = RM;
		LEFT_MOTOR = LM;

		RIGHT_EYE = RE;
		LEFT_EYE = LE;
		
		POWER = P;
		T_S = TS;
	}

	private static int getConditions() {

		int cond = 0;
		int rightEye = RIGHT_EYE.readNormalizedValue();
		int leftEye = LEFT_EYE.readNormalizedValue();

		if (rightEye >= leftEye)
			cond = 1;

		return cond;
	}

	public boolean takeControl() {
		return T_S.isPressed();
	}

	public void action() {


		RIGHT_MOTOR.backward();
		LEFT_MOTOR.backward();
		int cond = 0;

		while (!Button.ENTER.isDown()) {
			cond = getConditions();

			RIGHT_MOTOR.setSpeed(POWER[cond][0]);
			LEFT_MOTOR.setSpeed(POWER[cond][1]);

		}
		
	}

	public void suppress() {

		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		
	}

}
