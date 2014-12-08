import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class RandomSearch implements Behavior {

	private final NXTRegulatedMotor RIGHT_MOTOR;
	private final NXTRegulatedMotor LEFT_MOTOR;
	private final int[][] POWER;

	private static final int SLEEP_TIME = 75;

	public RandomSearch(NXTRegulatedMotor rm, NXTRegulatedMotor lm,
			UltrasonicSensor us, int[][] p) {
		this.RIGHT_MOTOR = rm;
		this.LEFT_MOTOR = lm;
		this.POWER = p;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		RIGHT_MOTOR.setSpeed(POWER[2][1]);
		LEFT_MOTOR.setSpeed(POWER[2][0]);

		RIGHT_MOTOR.forward();
		LEFT_MOTOR.forward();

		sleep((long) (SLEEP_TIME * Math.random()));

		int angle = (int) (180 * Math.random());
		boolean pos = Math.random() >= .5;
		RIGHT_MOTOR.rotate(pos ? angle : -angle);
		LEFT_MOTOR.rotate(pos ? -angle : angle);
	}

	@Override
	public void suppress() {
	}

	private void sleep(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
