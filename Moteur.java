package S5;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;

public class Moteur {
	
	private static final double DIAMETRE = 5.6;
	private MovePilot pilot;
	private float direction =0;
	private RegulatedMotor pinces;
	
	

	public Moteur () {
		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,DIAMETRE ).offset(-6.2);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, DIAMETRE).offset(6.2);
		Chassis chassis = new WheeledChassis(new Wheel[] {roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pinces = Motor.D;
	}
	
	
	public MovePilot getPilot() {
		return pilot;
	}
	
	public void reinitialiseDirection() { direction = 0;}
	
	public void tourner(double angle) {
		pilot.rotate(angle);
		direction += angle;	
		direction= direction%360;
	}
	
	public void tournerSync(double angle) {
		pilot.rotate(angle, true);
		direction += angle;
		direction= direction%360;
	}
	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}
	public void changerVitLin(double s) {
		pilot.setLinearSpeed(s);
	}
	public void changerVitRot(double s) {
		pilot.setAngularSpeed(s);
	}
	public void forward() {
		pilot.forward();
	}
	public void avancer(double distance) {
		pilot.travel(distance);
		direction += distance/20;
		direction= direction%360;
	}
	public void avancerSync(double distance) {
		pilot.travel(distance, true);
		direction += distance/20;
		direction= direction%360;
	}
	public void ouvrirPinces(int angle) {

	        int vitesseMoteur = 1000;

	        pinces.setSpeed(vitesseMoteur);
	        pinces.rotateTo(angle);;
	        while (pinces.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        pinces.stop();
	    }
	 public void fermerPinces(int angle) {

	        int vitesseMoteur = 1000;

	        pinces.setSpeed(vitesseMoteur);
	        pinces.rotate(-angle);
	        while (pinces.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        pinces.stop();
	    }
	 public boolean isMoving() {
		 return pilot.isMoving();
		 }
	 public void stop() {
		 pilot.stop();
	 }
	 
		public static void main(String[] args) {
			Moteur m = new Moteur();
			//m.avancer(-50);
			m.ouvrirPinces(100);
		}
}
