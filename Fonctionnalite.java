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

	public void premier_palet() {
		// Méthode qui marque le premier palet
		m.avancerSync(60); m.ouvrirPinces(1200); m.stop();
		m.fermerPinces(1200);
		m.tourner(90); m.avancer(15); m.tourner(-80); 
		m.avancer(190);
		m.ouvrirPinces(1200); m.avancer(-30); m.fermerPinces(1200);
		m.tourner(-m.getDirection()); 
		Delay.msDelay(2000);
		m.tourner(120);
	}

	
	public void perp_apres_essai() { 
		// Ce programme oriente le robot perpendiculaire au mur 
		m.tourner(90);
		m.changerVitRot(15);
		m.tournerSync(-180);
		float[] tableau = c.getSample(45, 250);
		int angle_perp = 0;
		float min = tableau[0]; // On initialise min avec la première valeur du tableau
		for (int i = 1; i < tableau.length; i++)
			if (tableau[i] < min) {
				angle_perp = i;
				min = tableau[i]; // Si on trouve une valeur plus petite, on met à jour min
			}
		m.changerVitRot(45);
		m.tourner((44 - angle_perp)*4);
	}	
	
	public void avancerVersPalet(int angle_de_balayage, int delay) {
		// Premiere méthode qui va orienter le robot vers le palet le plus proche.
		float[] distances = c.getSample(angle_de_balayage, delay); // On crée un tableau de float avec des distances, sur 120 degres ou 360 en fonction de la situation
		List<Float> positions_potentielles = detecterPalet(distances, angle_de_balayage);
		if (!positions_potentielles.isEmpty()) {
			int i = auPalet(positions_potentielles, angle_de_balayage);
			m.tourner(angle_de_balayage - i);
			m.avancer(distances[i]);		
		} else 
			System.out.println("No potential positions found.");
	}
	public List<Float> detecterPalet(float[] tab) {
		/*
  		* Méthode qui d'après le tableau de distances va chercher les mesures qui sont potentiellement des palets
    		* Il existera deux méthodes pour isoler les potentiels palets, leur distance au capteur(estDansLaPortee) et la distance entre les mesures(ecartImp).
    		*/
		List<Float> positions_potentielles = new ArrayList<Float>();
		for (int i = 0; i < tab.length - 1; i++) {
			if (ecartImp(tab, i) && estDansLaPortee(tab, i)) {
				positions_potentielles.add((float) i);
			}
		}
		return positions_potentielles;
	}
	
	public boolean ecartImp(float[] tab, int i) {
		// Si la distance deux à deux entre les mesures (1 mesure par angle)  est supérieur à 10 cm, on le classe dans la catégorie des palets potentielles
		if (Math.abs(tab[i - 1] - tab[i]) > 10 && Math.abs(tab[i] - tab[i + 1]) > 10) return true;
		return false;
	}

	public boolean estDansLaPortee(float[] tab, int i) {
		// Les palets ne sont détectés qu'entre 32 et 120 cm, on trie donc les mesures en conséquence. 
		if (tab[i] < 120 && tab[i] > 32) return true;
		return false;
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
		SampleProvider touchProvider = c.getTouche().getTouchMode();

		float[] sample = new float[touchProvider.sampleSize()];
		m.fermerPinces(1600);
		m.ouvrirPinces(1600);
		m.changerVitLin(100);
		m.avancer(60);
		while (!Button.ENTER.isDown() && sample[0] == 1.00) {
			touchProvider.fetchSample(sample, 0);
			try {
				Thread.sleep(500); // Attendez 1 seconde
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		m.fermerPinces(1600);
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

	/*
	 * public void ramenePalet() { une fois le palet récupéré, le ramène dans le
	 * camp rotateTo (180° - (angle du palet) + 90°) Ensuite avance jusqu'à détecter
	 * une ligne blanche
	 * 
	 * m.changerVitRot(36); m.tourner(180 - (angle_palet + 90));
	 * vers_ligne_arrivee(); }
	 */
	public void vers_ligne_arrivee() {
		if(! c.couleur.isFloodlightOn())  // teste si la lampe est allumée
			c.couleur.setFloodlight(true);
		m.changerVitLin(45);
		m.avancerSync(300);
		int colorID = c.couleur.getColorID();
		while (colorID!=6 && !Button.ENTER.isDown()) {
			System.out.print(colorID);
			colorID = c.couleur.getColorID();
			try {Thread.sleep(100);}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		m.stop();
		c.couleur.close();
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
/*
package S5;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Fonctionnalite {
	Moteur m;
	Capteur c;
	boolean arreter = false;
	static int cpt_palet=0;
	static boolean enCours = true;

	
	public Fonctionnalite() {
		m = new Moteur();
		c = new Capteur();
	}
	public void vers_ligne_arrivee(int couleur ) {
		if(! c.getCouleur().isFloodlightOn())  // teste si la lampe est allumée
			c.getCouleur().setFloodlight(true);
		m.changerVitLin(30);
		m.avancerSync(300);
		int colorID=-1;
		while (colorID!= couleur && !Button.ENTER.isDown()) {
			System.out.print(colorID);
			colorID = c.getCouleur().getColorID();
			try {Thread.sleep(100);}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		m.stop();
	}
	
	public void perpendiculaire(){
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		float last, min, d1;
		boolean notfound=true;
		distanceProvider.fetchSample(sample, 0);
		last=sample[0]*100;
		m.changerVitRot(36);
		m.tournerSync(360);
		while(notfound) {
			distanceProvider.fetchSample(sample, 0);
			d1=sample[0]*100;
			if(d1<last) {
				
			}
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
	
	public void troisPremiers() {

		SampleProvider distanceProvider = this.c.getUltrason().getDistanceMode(); 
		float[] sample = new float[distanceProvider.sampleSize()];
		 m.avancerSync(60); m.ouvrirPinces(1200); m.stop();
		 m.fermerPinces(1200);
		// hasPalet=true;
		 m.tourner(100); m.avancer(20); m.tourner(-110); 
		 this.vers_ligne_arrivee(6);
		 m.ouvrirPinces(1200);
		 cpt_palet++;
			 
		 

		while(cpt_palet<3 || !Button.ENTER.isDown()) {
			 m.avancer(-30); m.fermerPinces(1200); //hasPalet=false;
			 m.tourner(-m.getDirection()); 
			 Delay.msDelay(2000);
			 m.tourner(-120);	
			m.changerVitRot(36);
		int i = 0;
			
		ArrayList<Float> samples = new ArrayList<Float>();
		  m.tournerSync(-100); while(m.isMoving()) {
			  distanceProvider.fetchSample(sample, 0); samples.add(sample[0] * 100); //Convertir en centimètres 
			  System.out.println(i+"- "+samples.get(i)); i++; }
			  
			  double min=120;
		
			  for(int j=0; j<samples.size();j++) {
				  if(samples.get(j) < min && samples.get(j) >= 32) { min=samples.get(j); i=j; }
			  }
			  System.out.println("minimum :" +min); Delay.msDelay(3000);
			  System.out.println("size : " + samples.size()); Delay.msDelay(3000);
			  System.out.println("premier : "+ samples.get(0)); Delay.msDelay(3000);

			  m.changerVitRot(90);
			  //if(i<=180) { m.tourner(i); }else m.tourner(i-360);
			  m.tourner(+100-i*100.000/samples.size()-10);
			  
			  System.out.println("en avance");
			  m.ouvrirPinces(1200);
			  m.avancer(min);
			  m.stop();
			  m.fermerPinces(1200);
			  m.tourner(-m.getDirection());
			  this.vers_ligne_arrivee(6);
			  m.ouvrirPinces(1200);
			  cpt_palet++;
			  Delay.msDelay(2000);
			  }
		
	}
	
	//--------------
	public void perp() {
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		ArrayList<Float> samples = new ArrayList<Float>();
		m.changerVitRot(36);
		int i=0;
		double coef;
		m.tournerSync(360);
		while(m.isMoving()) {
			 distanceProvider.fetchSample(sample, 0);
		     samples.add(sample[0] * 100);  // Convertir en centimètres
	         System.out.println(samples.get(i));
	         i++;
		}
		
		  float min=samples.get(0);
		  for(float j : samples ) { 
			  if(min<32) 
			  { min=samples.get(samples.indexOf(j)); 
			  i=samples.indexOf(j); 
			  } 
			  if(samples.get(samples.indexOf(j)) < min && samples.get(samples.indexOf(j)) >= 32) { 
				  min=samples.get(samples.indexOf(j)); i=samples.indexOf(j); 
				  } 
			  }
	}
	
	
	
	public boolean ecartImp(float[] tab,int i) {
		if(Math.abs(tab[i-1] - tab[i]) > 10 && Math.abs(tab[i] - tab[i+ 1])> 10) {
			return true;
		}
		return false;
	    }

	public boolean estDansLaPortee(float[] tab, int i) {
	    if(tab[i]<120 && tab[i]>32){
	    	return true;
	    	}
	    return false;
	        }
	int angle_de_balayage  = 360; // cet angle peut varier selon les situations
	public List<Float> detecterPalet(float[] tab, int angle_de_balayage) {
	    List<Float> positions_potentielles = new ArrayList<>();
	    for (int i = 1; i < tab.length - 1; i++) {
	        if (ecartImp(tab, i) && estDansLaPortee(tab, i)) {
	            positions_potentielles.add((float) (i * angle_de_balayage) / tab.length);
	        }
	    }
	    return positions_potentielles;
	}
	public void cestUnPalets(float[] tab,List<Float> positions_potentielles,int angle_de_balayage) {
		float minimum = positions_potentielles.get(0); 
		for (float i : positions_potentielles) {
			if (i < minimum) {
				minimum = i;
				}
			}
		if (minimum <= 180) {
			m.tourner((int) minimum);
		}
		m.tourner((int) minimum-360);
		double distance = (double) tab[(int) ((minimum * tab.length)/ angle_de_balayage)];
		m.avancer(distance);	
	}
	
	
	public void detectionPalet() {
			float distanceDetectee;
			m.changerVitLin(20);
			m.forward();
				do {
			distanceDetectee= c.echantillon();
			System.out.println(distanceDetectee*100);
				}while(distanceDetectee*100 > 40 | Button.ENTER.isDown() | m.isMoving());
				System.out.println("palet detecte");
				m.stop();
				m.avancerSync(40);
				m.ouvrirPinces(1600);
				
				m.fermerPinces(1600);
				m.forward();
		
	}
	public void chercherPalet() {
		float d;
		m.changerVitRot(36);
		m.tournerSync(180);
		while(m.isMoving()) {
			d=c.echantillon();
			System.out.println(d*100);
			Delay.msDelay(0);
		}
	}
	public void enFace() {
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		ArrayList<Float> samples = new ArrayList<Float>();
		m.changerVitRot(36);
		int i = 0;
			
		  m.tournerSync(360); while(m.isMoving()) {
			  distanceProvider.fetchSample(sample, 0); samples.add(sample[0] * 100); //Convertir en centimètres 
			  System.out.println(samples.get(i)); i++; }
			  
			  double min=120;
			  for(int j=0; j<samples.size();j++) {
				  if(samples.get(j) < min && samples.get(j) >= 32) { min=samples.get(j); i=j; }
			  }
			  System.out.println("minimum :" +min); Delay.msDelay(3000);
			  System.out.println("size : " + samples.size()); Delay.msDelay(3000);
			  System.out.println("premier : "+ samples.get(0)); Delay.msDelay(3000);

			  m.changerVitRot(360);
			  //if(i<=180) { m.tourner(i); }else m.tourner(i-360);
			  m.tourner(i*360.0000/samples.size());
			  m.avancerSync(min);
				 while(m.isMoving()) {
				 distanceProvider.fetchSample(sample, 0);
				 if(sample[0]<=31) {
					 enFace();
				 }}
	
	}
	
	/*
	 * public void avanceAuPalet() {
	 * 
	 * EV3UltrasonicSensor ultrasonicSensor = new
	 * EV3UltrasonicSensor(SensorPort.S4); SampleProvider distanceProvider =
	 * ultrasonicSensor.getDistanceMode(); float[] sample = new
	 * float[distanceProvider.sampleSize()]; m.avancerSync(this.enFace());
	 * while(m.isMoving()) { distanceProvider.fetchSample(sample, 0);
	 * if(sample[0]<=31) m.stop(); } m.tourner(90); }
	 */
	
	
		
/*		Moteur m = new Moteur();
		m.changerVitLin(5);
		m.avancerSync(20);
		m.changerVitLin(100);
		m.avancerSync(20);*/
	
	}

}
