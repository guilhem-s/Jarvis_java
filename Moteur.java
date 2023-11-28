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
	
	private static final int RAPIDE = 1000;
	private static final int LENT = 100;
	private static final int RECHERCHE = 30;
	private static final double DIAMETRE = 5.6;
	private MovePilot pilot;
	private float direction =0;
	
	

	public Moteur () {
		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,DIAMETRE ).offset(-6.2);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, DIAMETRE).offset(6.2);
		Chassis chassis = new WheeledChassis(new Wheel[] {roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
	}
	
	public MovePilot getPilot() {
		return pilot;
	}
	
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
	        while (Motor.D.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        Motor.D.stop();
	    }
	 public boolean isMoving() {
		 return pilot.isMoving();
		 }
	 public void stop() {
		 pilot.stop();
	 }
