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

	public DropPuck(NXTRegulatedMotor RM, NXTRegulatedMotor LM, int[][] P,
			double M_L, LightSensor RE, LightSensor LE) {

		RIGHT_MOTOR = RM;
		LEFT_MOTOR = LM;

		RIGHT_EYE = RE;
		LEFT_EYE = LE;

		POWER = P;

		MAX_LIGHT = M_L;
	}

	public boolean takeControl() {
		// Driver.writeStatus("Drop Puck", "Take Control", "");
		if((RIGHT_EYE.getLightValue() + LEFT_EYE.getLightValue()) / 2.0 >= MAX_LIGHT && Driver.PHOTOTROPE_STATE) {
			Driver.PHOTOTROPE_STATE = false;
			return true;
		} else
			return false;
	}

	public void action() {
		Driver.writeStatus("Drop Puck", "Action", "");
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.setSpeed(POWER[2][0]);
		LEFT_MOTOR.setSpeed(POWER[2][1]);

		RIGHT_MOTOR.forward();
		LEFT_MOTOR.forward();

		sleep(1000);
		// Turn away from wall
		RIGHT_MOTOR.rotate(-180);
		LEFT_MOTOR.rotate(180);
	}

	public void suppress() {
		Driver.writeStatus("Drop Puck", "Suppress", "");
	}

	private void sleep(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
