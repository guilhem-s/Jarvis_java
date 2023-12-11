package S5;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;


 /**
 * La classe Moteur
 */ 
public class Moteur {
	
	private static final double DIAMETRE = 5.6;
	private MovePilot pilot;
	private float direction =0;
	private boolean pincesOuvertes=false;
	


/** 
 *
 * Affecte le parametres à l'attribut pincesOuvertes
 *
 * @param pincesOuvertes  true si les pinces sont ouverts, false sinon. 
 */
	public void setPincesOuvertes(boolean pincesOuvertes) { 

		this.pincesOuvertes = pincesOuvertes;
	}


/** 
 *
 * C'est un constructeur. 
 *
 */
	public Moteur () { 

		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,DIAMETRE ).offset(-6.2);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, DIAMETRE).offset(6.2);
		Chassis chassis = new WheeledChassis(new Wheel[] {roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
	}
	

/** 
 *
 * Renvoie l'objet MovePilot
 *
 * @return pilot
 */
	public MovePilot getPilot() { 

		return pilot;
	}
	

/** 
 *
 * tourne de l'angle passé en params en degrès
 *
 * @param angle  l'angle de rotation. 
 */
	public void tourner(double angle) { 

		pilot.rotate(angle);
		direction += angle;	
		direction= direction%360;
	}
	

/** 
 *
 * rotation asynchrone de l'angle en parametères
 *
 * @param angle l'angle de rotation. 
 */
	public void tournerSync(double angle) { 
		pilot.rotate(angle, true);
		direction += angle;
		direction= direction%360;
	}

/** 
 *
 * renvoie la direction, 0 si le but adverse est à 0° du robot
 *
 * @return the direction en degrès
 */
	public float getDirection() { 

		return direction;
	}


/** 
 *
 * Affecte le param à l'attribut direction
 *
 * @param direction  la direction en degrès. 
 */
	public void setDirection(float direction) { 

		this.direction = direction;
	}

/** 
 *
 * Change la vitesse linéaire
 *
 * @param s  vitesse en degrès par seconde. 
 */
	public void changerVitLin(double s) { 

		pilot.setLinearSpeed(s);
	}

/** 
 *
 * Change la vitesse de rotation
 *
 * @param s  vitesse de rotation en degrès par seconde 
 */
	public void changerVitRot(double s) { 

		pilot.setAngularSpeed(s);
	}

/** 
 *
 * Avance
 *
 */
	public void forward() { 
		pilot.forward();
	}

/** 
 *
 * Avance de la distance en parametres
 *
 * @param distance  la distance en cm. 
 */
	public void avancer(double distance) { 

		pilot.travel(distance);
		direction += distance/40;
		direction= direction%360;
	}

/** 
 *
 * Avancement asynchrone de la distance en parametres
 *
 * @param distance  la distance en cm. 
 */
	public void avancerSync(double distance) { 

		pilot.travel(distance, true);
		direction += distance/40;
		direction= direction%360;
	}

/** 
 *
 * ouvre les pinces
 *
 * @param angle   angle = 1200 pour écarter les pinces. 
 */
	public void ouvrirPinces(int angle) { 


	        int vitesseMoteur = 1000;
	        if(!pincesOuvertes) {
	        Motor.D.setSpeed(vitesseMoteur);
	        Motor.D.rotateTo(angle);;
	        while (Motor.D.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        pincesOuvertes=true;
	        }
	        Motor.D.stop();
	    }

/** 
 *
 * ferme les pinces 
 *
 * @param angle   angle = 1200 pour serrer les pinces. 
 */
	 public void fermerPinces(int angle) { 
	        int vitesseMoteur = 1000;
	        if(pincesOuvertes) {
	        Motor.D.setSpeed(vitesseMoteur);
	        Motor.D.rotate(-angle);
	        while (Motor.D.isMoving()) {
	            Delay.msDelay(100); // Wait for 100 milliseconds
	        }
	        pincesOuvertes=false;
	        }
	        Motor.D.stop();
	    }
	 
/** 
 *
 * renvoie true si le robot est en mouvement, false sinon
 *
 * @return boolean 
 */
	 public boolean isMoving() { 

		 return pilot.isMoving();
		 }

/** 
 *
 * Arrete le robot
 *
 */
	 public void stop() { 

		 pilot.stop();
	 }
}
