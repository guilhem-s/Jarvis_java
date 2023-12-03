package S5;

import java.util.ArrayList;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class Fonctionnalite {
	Moteur m;
	Capteur c;
	boolean arreter = false;
	int cpt_palet=0;


	public Fonctionnalite() {
		m = new Moteur();
		c = new Capteur();
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
		int d;
		m.tourner(-60);
		ArrayList<Integer> mur = c.echantillon_en_tournant(m, 140, 10000, 20);
		int min = mur.get(0); // On initialise min avec la première valeur du tableau
		for(int j=0; j<mur.size();j++) { if(mur.get(j) < min) { min=mur.get(j); }}		
		m.tournerSync(-140);
		do {
			d = c.echantillon(10000);
		} while (d != min);
		m.stop();
		m.changerVitRot(60);
		m.reinitialiseDirection();
	}	
	
	public void ligneNoire() {
		// Cette méthode place notre robot dos à la ligne d'en-buts adverse sur la ligne noire qui coupe le terrain en longueur 		
		
		perp_apres_essai();// algo qui se met perpendiculaire au mur
		
		int murDroit;
		m.tourner(90);Delay.msDelay(500); //On se met face au mur de droite	
		murDroit=c.echantillon(100);
		System.out.println(murDroit); Delay.msDelay(5000);// On mesure notre distance par rapport à cette distance
		m.avancer(murDroit-87); //moitié de la table = 100, et il y a 13 cm entre les roues et le capteur
		m.tourner(90);// On se met dos à la ligne d'en-buts
	}
	
	public void premier_palet() {
		// Méthode qui marque le premier palet
		m.avancerSync(60); m.ouvrirPinces(1200); m.stop();
		m.fermerPinces(1200);
		m.tourner(100); m.avancer(20); m.tourner(-100); 
		vers_couleur(6);
		m.ouvrirPinces(1200);
		cpt_palet++;
		m.avancer(-10); m.fermerPinces(1200); 
	}
	
	public void troisPremiers() {

		// Boucle qui récupére les trois premiers palets en prenant en compte que l'équipe adverse récupère un palet
		
		premier_palet();
		System.out.println(cpt_palet);
		
		while(cpt_palet<3) {
			// boucle qui récupère les 2 autres palets restants de la ligne la plus proche de la zone d'en-buts
			if(cpt_palet!=1) m.avancer(-10); m.fermerPinces(1200); 
			ligneNoire(); 
			m.avancer(-10); // On recule pour être sûr d'avoir tous les palets dans notre champ de vision
			facePalet();
			m.tourner(-m.getDirection());
			vers_couleur(6);
			m.ouvrirPinces(1200);
			cpt_palet++;
		}
	}
	
	public void facePalet() {
		// Place le robot face au palet et puis fait avancer le robot pour l'attraper
		int i = 0;
		m.tourner(-55);
		ArrayList<Integer> samples = c.echantillon_en_tournant(m, 110, 1000, 36);
		double minPalet=1200;

		for(int j=0; j<samples.size();j++) {
			if(samples.get(j) < minPalet && samples.get(j) >= 320) { minPalet=samples.get(j); i=j; }
		}
		System.out.println("minimum :" +minPalet); 
		System.out.println("size : " + samples.size()); 
		System.out.println("premier : "+ samples.get(0)); 

		m.changerVitRot(90);
		//if(i<=180) { m.tourner(i); }else m.tourner(i-360);
		if (i > samples.size()/2)  m.tourner(-110+i*110.000/samples.size() + 5);
		m.tourner(-110+i*110.000/samples.size());
		m.ouvrirPinces(1200);
		m.avancer(minPalet/10 + 5);
		m.fermerPinces(1200);
	}
	
	public void facePalet2() {
		// Place le robot face au palet et puis fait avancer le robot pour l'attraper
		int d;
		m.tourner(-55);
		ArrayList<Integer> samples = c.echantillon_en_tournant(m, 110, 100);
		double minPalet=120;

		for(int j=0; j<samples.size();j++) {
			if(samples.get(j) < minPalet && samples.get(j) >= 32) { minPalet=samples.get(j);}
		}
		System.out.println("minimum :" +minPalet); Delay.msDelay(3000);
		tempsDebutRot = System.currentTimeMillis();
		m.tournerSync(-110);
		do {
			d = c.echantillon(100);
		} while (d != minPalet);
		m.stop();
		float tempsEcoule = (float) (System.currentTimeMillis()- tempsDebutRot);
		m.setDirection(m.getDirection() + tempsEcoule/1000):
		m.ouvrirPinces(1200);
		m.avancer(minPalet + 5);
		m.fermerPinces(1200);
	}

	public void ligneCentrale() {

		while(cpt_palet<6) {
			m.avancer(-10);
			m.fermerPinces(1200);
			ligneNoire();
			m.avancer(45);// ou 50
			facePalet();
			m.tourner(-m.getDirection());
			vers_couleur(6);
			m.ouvrirPinces(1200);
			cpt_palet++;}}
	


	public static void main(String[] args) {
		Fonctionnalite t = new Fonctionnalite();
		Surveillance surveillance = new Surveillance();
		surveillance.lancerSurveillanceBouton();
		t.troisPremiers();
		t.ligneCentrale();
		surveillance.attendreFinSurveillance();
	}


}
