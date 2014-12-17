import java.util.Random;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class RandomSearch implements Behavior {

	private final NXTRegulatedMotor RIGHT_MOTOR;
	private final NXTRegulatedMotor LEFT_MOTOR;
	private final Random rGen = new Random();
	private final int[][] POWER;

	private static final int SLEEP_TIME = 600;

	public RandomSearch(NXTRegulatedMotor rm, NXTRegulatedMotor lm,
			UltrasonicSensor us, int[][] p) {
		this.RIGHT_MOTOR = rm;
		this.LEFT_MOTOR = lm;
		this.POWER = p;
	}

	@Override
	public boolean takeControl() {
	//	Driver.writeStatus("Search", "Take Control", "");
		return true;                                                                                   
	}

	@Override
	public void action() {
		Driver.writeStatus("Search", "Action", "");


		RIGHT_MOTOR.backward();
		LEFT_MOTOR.backward();
		
		RIGHT_MOTOR.setSpeed(POWER[2][0]);
		LEFT_MOTOR.setSpeed(POWER[2][1]);

		sleep((long) ((rGen.nextDouble() + 0.5) * SLEEP_TIME));
		
		int angle = rGen.nextInt(360) * (rGen.nextBoolean() ? -1 : 1);
		RIGHT_MOTOR.rotate(angle, false);
		LEFT_MOTOR.rotate(-angle, false);
	}

	@Override
	public void suppress() {
		Driver.writeStatus("Search", "Suppress", "");
	}

	private void sleep(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
