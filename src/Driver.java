import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {// speeds
	public static void main(String[] args) {
		int HIGH = 800; // 4/3 of MED (Safe @ 533)
		int LOW = 200; // 2/3 of MED (Safe @ 267)
		double MAX_LIGHT = 0;

		int[][] POWER = {
		/* LEFT */{ HIGH, LOW },
		/* RIGHT */{ LOW, HIGH },
		/* CENTER */{ HIGH, HIGH },
		/* FAST LEFT */{ HIGH * 4, LOW / 4 },
		/* FAST RIGHT */{ LOW / 4, HIGH * 4 } };

		NXTRegulatedMotor RIGHT_MOTOR = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor LEFT_MOTOR = new NXTRegulatedMotor(MotorPort.B);

		LightSensor RIGHT_EYE = new LightSensor(SensorPort.S1);
		LightSensor LEFT_EYE = new LightSensor(SensorPort.S2);

		RIGHT_EYE.setFloodlight(false);
		LEFT_EYE.setFloodlight(false);

		UltrasonicSensor U_S = new UltrasonicSensor(SensorPort.S4);

		TouchSensor T_S = new TouchSensor(SensorPort.S3);

		while (!Button.ENTER.isDown()) {
			MAX_LIGHT = (RIGHT_EYE.getLightValue() + LEFT_EYE.getLightValue()) / 2.0; // calibrating
																					// average
																					// highest
																					// light
			LCD.drawString(String.valueOf(MAX_LIGHT), 4, 4);
		}
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (!Button.ENTER.isDown()) {}

		Behavior b1 = new RandomSearch(RIGHT_MOTOR, LEFT_MOTOR, U_S, POWER);
		Behavior b3 = new Phototrope(RIGHT_MOTOR, LEFT_MOTOR, RIGHT_EYE,
				LEFT_EYE, POWER, T_S);
		Behavior b2 = new AvoidWall(RIGHT_MOTOR, LEFT_MOTOR, U_S, POWER);
		Behavior b4 = new DropPuck(RIGHT_MOTOR, LEFT_MOTOR, POWER, MAX_LIGHT,
				RIGHT_EYE, LEFT_EYE);

		Behavior[] bArray = { b1, b2, b3, b4 };
		Arbitrator arby = new Arbitrator(bArray);

		arby.start();

	}

	public static void writeStatus(String b, String s, String e) {
		LCD.drawString("Behav: " + b, 1, 0);
		LCD.drawString("Stage: " + s, 1, 1);
		LCD.drawString(e, 1, 2);
	}

}
