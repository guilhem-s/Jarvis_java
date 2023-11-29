package S5;


import java.util.HashMap;
import java.util.Map;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.nxt.LCP;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class DetectionCouleur {
	public static void main(String[] args) {

		//Convertir entier en string avec dictionnaire
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "Rouge");
		map.put(1, "Vert");
		map.put(2, "Bleu");
		map.put(3, "Jaune");
		map.put(6, "Blanc");
		map.put(7, "Noir");
		map.put(9, "Gris");
		map.put(10, "Gris");
		map.put(11, "Gris");        



		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		//if(! colorSensor.isFloodlightOn())  // teste si la lampe est allumée
		//colorSensor.setFloodlight(true);
		 Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,5.6 ).offset(-70);
			Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, 5.6).offset(70);
			Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
			MovePilot pilot = new MovePilot(chassis);
			CommandePinces p = new CommandePinces();
		while (true) {
			pilot.forward();
			int colorID = colorSensor.getColorID();
			System.out.println("Couleur detectee : " + map.get(colorID));
			try {
				Thread.sleep(1000); // Attendre 1 seconde entre les mesures
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(colorID==6) {
			p.ouvrirPinces(1600);break;}
			if(Button.ENTER.isDown())break;
		}
		colorSensor.close();
	}
}
