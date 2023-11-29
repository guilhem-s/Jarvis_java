package S5;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class CommandePinces {
    public void ouvrirPinces(int angle) {

        int vitesseMoteur = 1000;

        Motor.D.setSpeed(vitesseMoteur);
        Motor.D.rotateTo(angle);
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
    public static void main(String[] args) {
    	Motor.D.rotate(-1600);
    }
}