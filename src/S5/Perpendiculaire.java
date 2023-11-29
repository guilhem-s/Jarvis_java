package S5;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class Perpendiculaire {

	public static void main(String[] args) {
		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,5.6 ).offset(-7.1);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, 5.6).offset(7.1);
		Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		CommandePinces p = new CommandePinces();
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);
		SampleProvider touchProvider = touchSensor.getTouchMode();
		float[] sample = new float[touchProvider.sampleSize()];
		p.ouvrirPinces(1600);
		/*pilot.setLinearSpeed(10);
			pilot.travel(30, true);
		do {
			 touchProvider.fetchSample(sample, 0);
		     if(Button.ENTER.isDown())break;
		}while(sample[0]!=1.0);
		p.fermerPinces(1600);
		pilot.travel(30);*/
		
	}

}
