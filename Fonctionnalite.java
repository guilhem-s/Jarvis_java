package S5;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Fonctionnalite {
	private static final int ANGLE_BALAYAGE = 360;
	Moteur m;
	Capteur c;

	public Fonctionnalite() {
		m = new Moteur();
		c = new Capteur();
	}

	public void perpendiculaire_apres_essai() {

		float d1, d2;
		m.changerVitRot(90);
		d1 = c.echantillon();
		m.tourner(ANGLE_BALAYAGE);
		Delay.msDelay(200);
		d2 = c.echantillon();
		System.out.println("d1 = " + d1 + " d2 = " + d2);
		Delay.msDelay(1000);
		if (d1 > d2) {
			System.out.print("continue dans le meme sens");
			m.tournerSync(90);
			d1 = d2;
			d2 = c.echantillon();
			System.out.println("d1 = " + d1 + " d2 = " + d2);
			while (d1 >= d2) {
				System.out.print("Rentre dans la boucle");
				Delay.msDelay(250);
				d1 = d2;
				d2 = c.echantillon();
				System.out.println("d1 = " + d1 + " d2 = " + d2);
			}
			m.stop();
			Delay.msDelay(40000);
		} else {
			System.out.print("Change de sens");
			m.tournerSync(-90);
			Delay.msDelay(500);
			d1 = d2;
			d2 = c.echantillon();
			while (d1 > d2) {
				System.out.print("Rentre dans la boucle");
				Delay.msDelay(250);
				d1 = d2;
				d2 = c.echantillon();
			}

			m.stop();
			Delay.msDelay(40000);
		}
	} 
	
		public float[] distanceTourne(int angle_de_balayage) {
		/*
		 * Creer un set de données sur (angle_de_balayage) degrés et 
		 * générer des données tous les (delay) millisecondes
		 */
		m.changerVitRot(5);
		m.tournerSync(angle_de_balayage);
		float[] distances = new float[angle_de_balayage];
		int i = 0;
		while(m.isMoving()) {
			distances[i] = c.echantillon();
			Delay.msDelay(200);
			i++;
		}
		return distances;
	}
	public boolean ecartImp(float[] tab, int i) {
		if (Math.abs(tab[i - 1] - tab[i]) > 10 && Math.abs(tab[i] - tab[i + 1]) > 10) {
			return true;
		}
		return false;
	}

	public boolean estDansLaPortee(float[] tab, int i) {
		if (tab[i] < 120 && tab[i] > 32) {
			return true;
		}
		return false;
	}

	int angle_de_balayage = 360; // cet angle peut varier selon les situations

	public List<Float> detecterPalet(float[] tab, int angle_de_balayage) {
		List<Float> positions_potentielles = new ArrayList<Float>();
		for (int i = 0; i < tab.length - 1; i++) {
			if (ecartImp(tab, i) && estDansLaPortee(tab, i)) {
				positions_potentielles.add((float) i);
			}
		}
		return positions_potentielles;
	}
	public void avancerVersPalet(int angle_de_balayage) {
		/*
		 * Fonction qui va aligner le robot vers le palet le plus proche.
		 */
		float[] distances = distanceTourne(angle_de_balayage);
		List<Float> positions_potentielles = detecterPalet(distances, angle_de_balayage);
		if (!positions_potentielles.isEmpty()) {
			int i = auPalet(positions_potentielles, angle_de_balayage);
			m.tourner(i);
			m.avancer(distances[i]);
		
		} else {
			System.out.println("No potential positions found.");
		}
	}
	public int auPalet(List<Float> positions_potentielles, int angle_de_balayage) {
		float minimum = positions_potentielles.get(0);
		for (float i : positions_potentielles) {
			if (i < minimum) {
				minimum = i;
			}
		}
		float moitie = angle_de_balayage/2;
		if (minimum <= moitie ){	
			return (int) minimum;
		} else {
			return (int) minimum - 360;
		}
	}

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
			if (sample[0] == 1.00) {
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
			distanceDetectee = c.echantillon();
			System.out.println(distanceDetectee * 100);
		} while (distanceDetectee * 100 > 30 | Button.ENTER.isDown() | m.isMoving());
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

	public float[] creation_tab() {
		m.changerVitRot(36);
		m.tournerSync(180);
		return c.getSample(10);
	}

	/*
	 * public void ramenePalet() { une fois le palet récupéré, le ramène dans le
	 * camp rotateTo (180° - (angle du palet) + 90°) Ensuite avance jusqu'à détecter
	 * une ligne blanche
	 * 
	 * m.changerVitRot(36); m.tourner(180 - (angle_palet + 90));
	 * vers_ligne_arrivee(); }
	 */
	public void vers_ligne_arrivee() {
		if (!c.getCapcouleur().isFloodlightOn()) // teste si la lampe est allumée
			c.getCapcouleur().setFloodlight(true);
		while (true) {
			m.avancerSync(100);
			int colorID = c.getCapcouleur().getColorID();
			if (colorID == 6)
				break;
			try {
				Thread.sleep(250); // Attendre 0,25 seconde entre les mesures
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Button.ENTER.isDown())
				break;
		}
		m.stop();
		c.getCapcouleur().close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fonctionnalite f = new Fonctionnalite();
		f.detectionPalet();
		/*
		 * Moteur m = new Moteur(); m.changerVitLin(5); m.avancerSync(20);
		 * m.changerVitLin(100); m.avancerSync(20);
		 */

	}

}
