package S5;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class Fonctionnalite {
	Moteur m;
	Capteur c;


	public Fonctionnalite() {
		m = new Moteur();
		c = new Capteur();
	}

	public void perpendiculaire_apres_essai() {
		int d1, d2;
		m.changerVitRot(5);
		d1 = c.echantillon();		
		m.tourner(5);
		Delay.msDelay(500);
		d2 = c.echantillon();

		if (d1 < d2) {
			m.tourner(-10);
			while (d1 > d2) {
				m.tourner(-5);
				d1 = d2;
				Delay.msDelay(100);
				d2 = c.echantillon();
			}
			m.tourner(5);

		} else {
			while (d1 > d2) {
				m.tourner(5);
				d1 = d2;
				Delay.msDelay(100);
				d2 = c.echantillon();
			}
			m.tourner(-5);
		}
	}

	public void detectionPalet(float seuilDetection) {
		while (true) {
			float distanceDetectee = c.echantillon();
			if (distanceDetectee < seuilDetection) {
				System.out.println("palet détecté");
				while (!Button.ENTER.isDown()) {
					if (c.echantillon() == 1.00) {
						m.fermerPinces(1600);
						break;
					}

				}
				Delay.msDelay(500);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Moteur m = new Moteur();
	}

}
