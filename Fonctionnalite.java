package S5;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Fonctionnalite {
	Moteur m;
	Capteur c;

	
	public Fonctionnalite() {
		m = new Moteur();
		c = new Capteur();
	}
	public void perpendiculaire_apres_essai() {
		/*for(int i=0; i<100;i++) {System.out.println(c.echantillon()*100);
		Delay.msDelay(1000);
		if(Button.ENTER.isDown())break;}}*/
		float d1, d2;
		m.changerVitRot(80);
		d1 = c.echantillon();		
		m.tourner(15);
		Delay.msDelay(500);
		d2 = c.echantillon();
		System.out.println(d1+" "+d2+ " ");
		Delay.msDelay(1000);
		if (d1 < d2) {
			m.tourner(-30);
			d1 = d2;
			d2 = c.echantillon();
			while(d1 > d2) { 
				m.tourner(-15);
				Delay.msDelay(100);
				d1 = d2;
				d2 = c.echantillon();
				System.out.println(d1+" "+d2+ " ");
			}
			Delay.msDelay(5000);
			m.tourner(15);
		}}
		 /*else {
			while (d1 > d2) {
				m.tourner(5);
				d1 = d2;
				Delay.msDelay(100);
				d2 = c.echantillon();
				System.out.println(d2+ " ");
			}
		m.tourner(-5);
		}*/
	
	public void attraperPalet() {
		c.getTouche();
        SampleProvider touchProvider = c.getTouche().getTouchMode();

        float[] sample = new float[touchProvider.sampleSize()];
        m.fermerPinces(1600);
	m.ouvrirPinces(1600);
	m.changerVitLin(100);
	m.avancer(60);
	while (!Button.ENTER.isDown()) {
    touchProvider.fetchSample(sample, 0);
	if (sample[0]==1.00) {
		m.fermerPinces(1600);
		break;
	}
        try {
            Thread.sleep(1000); // Attendez 1 seconde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	}
	public void detectionPalet() {
			float distanceDetectee;
			m.changerVitLin(20);
			m.avancerSync(300); 
			
				do {
			distanceDetectee= c.echantillon();
			System.out.println(distanceDetectee*100);
				}while(distanceDetectee*100 > 30 | Button.ENTER.isDown() | m.isMoving());
				m.stop();
			
				m.ouvrirPinces(1600);
				System.out.println("palet detecte");
				while (!Button.ENTER.isDown()) {
					if (c.echantillon() == 1.00) {
						m.fermerPinces(1600);
						break;
					}

				}
				m.avancerSync(60);
				m.fermerPinces(1600);
				Delay.msDelay(500);
			
		
	}


	public void versPalet(float seuilDetection) { // a voir quelle méthode est la meilleure
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
	
	public int[] detecterPalet(float[] tab) {
		/* Fonctions qui va détecter les grands écarts entre valeurs voisines du tableau pour les répertorier 
		 * Ces positions seront potentielles et un autre test avec un capteur devra être efféctué afin d'assure
		 * la vraie présence d'un palet
		 */
		int[] positions_potentielles = new int[tab.length];
		int compteur = 0;
		for(int i = 1; i < tab.length - 1; i++)
			if(Math.abs(tab[i-1] - tab[i]) > 10 && Math.abs(tab[i] - tab[i+ 1])> 10 && tab[i]<120 && tab[i]>32) {
				positions_potentielles[compteur] = i;
				compteur++;
			}
		return positions_potentielles;
		}
	
	public float[] creation_tab() {
		m.changerVitRot(36);
		m.tournerSync(180);
		return c.getSample(10);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fonctionnalite f = new Fonctionnalite();
		f.detectionPalet();
/*		Moteur m = new Moteur();
		m.changerVitLin(5);
		m.avancerSync(20);
		m.changerVitLin(100);
		m.avancerSync(20);*/
	
	}

}
