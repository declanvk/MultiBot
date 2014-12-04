import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {// speeds
	public static void main(String[] args) {
		int HIGH = 800; // 4/3 of MED (Safe @ 533)
		int LOW = 200; // 2/3 of MED (Safe @ 267)
		
		int[][] POWER = {
		/* LEFT */{ HIGH, LOW },
		/* RIGHT */{ LOW, HIGH } };

		NXTRegulatedMotor RIGHT_MOTOR = new NXTRegulatedMotor(
				MotorPort.A);
		NXTRegulatedMotor LEFT_MOTOR = new NXTRegulatedMotor(
				MotorPort.B);

		LightSensor RIGHT_EYE = new LightSensor(SensorPort.S1);
		LightSensor LEFT_EYE = new LightSensor(SensorPort.S2);

		Behavior b1 = new Phototrope(RIGHT_MOTOR, LEFT_MOTOR, RIGHT_EYE, LEFT_EYE, POWER);
		Behavior b2 = new RandomSearch();
		
		Behavior[] bArray = {b1, b2};
		Arbitrator arby = new Arbitrator(bArray);
		
		arby.start();

	}

}
