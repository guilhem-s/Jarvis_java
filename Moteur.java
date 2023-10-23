package S5;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;

public class Moteur {
	
	private static final int RAPIDE = 1500;
	private static final int LENT = 100;
	private static final int RECHERCHE = 100;
	private static final double DIAMETRE = 5.6;
	private MovePilot pilot;
	
	public Moteur () {
		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,DIAMETRE ).offset(-70);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, DIAMETRE).offset(70);
		Chassis chassis = new WheeledChassis(new Wheel[] {roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
	}
	
	public MovePilot getPilot() {
		return pilot;
	}
	
	public void tourner(int angle) {
		pilot.setAngularSpeed(5);
		pilot.rotate(angle);		
	}
	
	public void tournerSync(int angle) {
		pilot.rotate(angle, true);
	}
	public void changerVitLin(double s) {
		pilot.setLinearSpeed(s);
	}
	public void changerVitRot(double s) {
		pilot.setAngularSpeed(s);
	}
	public void avancer(double distance) {
		pilot.travel(distance);
	}
	public void avancerSync(double distance) {
		pilot.travel(distance, true);
	}
	public void ouvrirPinces(int angle) {

	        int vitesseMoteur = 1000;

	        Motor.D.setSpeed(vitesseMoteur);
	        Motor.D.rotateTo(angle);;
	        while (Motor.D.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        Motor.D.stop();
	    }
	 public void fermerPinces(int angle) {

	        int vitesseMoteur = 1000;

	        Motor.D.setSpeed(vitesseMoteur);
	        Motor.D.rotate(-angle);

	        Delay.msDelay(2000); 

	        Motor.D.stop();
	    }
	 public boolean isMoving() {
		 return pilot.isMoving();
		 }
	 public void stop() {
		 pilot.stop();
	 }
}
}
