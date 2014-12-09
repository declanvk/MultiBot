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
		
		ULTRA_SENSOR.continuous();
	}

	@Override
	public boolean takeControl() {
		//Driver.writeStatus("Avoid Wall", "Take Control", Integer.toString(ULTRA_SENSOR.getDistance()));
		return ULTRA_SENSOR.getDistance() < 20;
	}

	@Override
	public void action() {
		Driver.writeStatus("Avoid Wall", "Action", "");
		// Set up backing up from wall
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.setSpeed(POWER[2][1]);
		LEFT_MOTOR.setSpeed(POWER[2][0]);
		RIGHT_MOTOR.forward();
		LEFT_MOTOR.forward();

		// Backup for 20 milliseconds
		sleep(200);
		

		// Turn away from wall
		RIGHT_MOTOR.rotate(-90);
		LEFT_MOTOR.rotate(90);
	}

	@Override
	public void suppress() {
		Driver.writeStatus("Avoid Wall", "Suppress", "");
	}

	private void sleep(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
