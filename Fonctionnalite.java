package S5;

import lejos.hardware.Button;
import java.util.ArrayList;
import java.util.List;
import lejos.utility.Delay;

public class test {
	Moteur m;
	Capteur c;
	boolean arreter;
	static int cpt_palet;
	static boolean enCours;

	public test() {
		m = new Moteur();
		c = new Capteur();
		arreter = false;
		cpt_palet = 0;
		enCours = true;
	}

	public void vers_couleur(int couleur ) {
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

	
	public void perp_apres_essai() { 
		// Ce programme oriente le robot perpendiculaire au mur 
		m.tourner(90);
		m.changerVitRot(15);
		m.tournerSync(-180);
		int[] tableau = c.getSample(45, 250);
		int angle_perp = 0;
		float min = tableau[0]; // On initialise min avec la première valeur du tableau
		for (int i = 1; i < tableau.length; i++)
			if (tableau[i] < min) {
				angle_perp = i;
				min = tableau[i]; // Si on trouve une valeur plus petite, on met à jour min
			}
		m.changerVitRot(45);
		m.tourner((44 - angle_perp)*4);
		m.reinitialiseDirection();;
	}	
	
	public void premierPalet() {
		m.avancerSync(55); m.ouvrirPinces(1200); m.stop();
		m.fermerPinces(1200);
		m.tourner(85); m.avancer(30); m.tourner(-92); 
		vers_couleur(6);
		m.ouvrirPinces(1200);
	}
	
	public void troisPremiers() {

		premierPalet();
		cpt_palet++;

		while(cpt_palet<3) {
			m.avancer(-20); m.fermerPinces(1200);
			m.tourner(-m.getDirection());
			Delay.msDelay(2000);
			m.tourner(-100);
			
			m.changerVitRot(36);
			m.tournerSync(-140);
			ArrayList<Integer> samples = c.echantillon_en_tournant(m, 120);
			
			int distance_au_palet = 0; // longueur entre le robot et le palet
			int position_dans_la_file = 0; // indice du palet dans le tableau de mesures 
			
			for(int j=1; j<samples.size();j++) { // condition pour trouver le palet le plus proche
				int distance = samples.get(j);
				if( distance < 120 && distance >= 32 && Math.abs(distance - samples.get(j-1)) > 5) { 
					distance_au_palet = distance;
					position_dans_la_file = j;
				}
			}
			System.out.println("minimum :" +distance_au_palet); Delay.msDelay(3000);// Faudra pas 
			System.out.println("size : " + samples.size()); Delay.msDelay(3000); //    Oublier de
			System.out.println("premier : "+ samples.get(0)); Delay.msDelay(3000); // Supprimer ça 

			m.changerVitRot(90);
			//if(i<=180) { m.tourner(i); }else m.tourner(i-360);
			m.tourner(+100-position_dans_la_file*100.000/samples.size()-10);
			
			attraper_palet(distance_au_palet);
			cpt_palet++;
			Delay.msDelay(2000);
		}
	}

	public void attraper_palet(int distance) {
		System.out.println("en avance");
		m.ouvrirPinces(1200);
		m.avancer(distance);
		m.stop();
		m.fermerPinces(1200);
		m.tourner(-m.getDirection());
		vers_couleur(6);
		m.ouvrirPinces(1200);
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


	
	public static void main(String[] args) {
		test t = new test();
		Surveillance surveillance = new Surveillance();
		surveillance.lancerSurveillanceBouton();
		t.troisPremiers();
		Delay.msDelay(5000);
		t.perp_apres_essai();
		t.m.fermerPinces(1200);
        	surveillance.attendreFinSurveillance();
	}
}
