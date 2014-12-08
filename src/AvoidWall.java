import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class AvoidWall implements Behavior {

	private final NXTRegulatedMotor RIGHT_MOTOR;
	private final NXTRegulatedMotor LEFT_MOTOR;
	private final UltrasonicSensor ULTRA_SENSOR;
	private final int[][] POWER;

	public AvoidWall(NXTRegulatedMotor rm, NXTRegulatedMotor lm,
			UltrasonicSensor us, int[][] p) {
		this.RIGHT_MOTOR = rm;
		this.LEFT_MOTOR = lm;
		this.ULTRA_SENSOR = us;
		this.POWER = p;
	}

	@Override
	public boolean takeControl() {
		return ULTRA_SENSOR.getDistance() < 30;
	}

	@Override
	public void action() {
		// Set up backing up from wall
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.setSpeed(POWER[2][1]);
		LEFT_MOTOR.setSpeed(POWER[2][0]);
		RIGHT_MOTOR.backward();
		LEFT_MOTOR.backward();

		// Backup for 20 milliseconds
		sleep(20);
		

		// Turn away from wall
		RIGHT_MOTOR.rotate(-45);
		LEFT_MOTOR.rotate(45);
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
