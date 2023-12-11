package S5;

import java.util.ArrayList;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
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
	
	public void avancerSurveille(double d) {
		SampleProvider distanceProvider = c.getUltrason().getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		m.avancerSync(d);
		while(m.isMoving()) {
			distanceProvider.fetchSample(sample, 0);
			if(sample[0]<15) {
				m.tourner(45);
				m.avancer(30);
				m.tourner(-m.getDirection());
				m.stop();
				m.avancerSync(d);
			}
		}
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
	
	public void attraperPalet() {
		c.getTouche();
        SampleProvider touchProvider = c.getTouche().getTouchMode();

        float[] sample = new float[touchProvider.sampleSize()];
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
	
	public void ligneNoire() {
		SampleProvider distanceProvider = this.c.getUltrason().getDistanceMode(); 
		float[] sample = new float[distanceProvider.sampleSize()];
		float murDroit=0;
		int i=0;
		m.tourner(-m.getDirection());
		m.changerVitRot(36);
		m.tourner(-20);
		ArrayList<Float> mur = new ArrayList<Float>();
		double min=300;
		m.tournerSync(40); 
		while(m.isMoving()) {
			distanceProvider.fetchSample(sample, 0); mur.add(sample[0] * 100);
		}
		  				for(int j=0; j<mur.size();j++) {
		  					if(mur.get(j) < min) { min=mur.get(j); i=j; }
		  				}
		m.tourner(-40+i*40.000/mur.size()+5);
		m.changerVitRot(72);
		m.tourner(90);
		m.changerVitRot(36);
		Delay.msDelay(500);
		distanceProvider.fetchSample(sample, 0); //Convertir en centimètres 
		murDroit=sample[0]*100;
		
		m.avancer(murDroit-87); //-87
		m.tourner(90);
	}
	public void premierPalet() {
		m.avancerSync(60); m.ouvrirPinces(1200); m.stop();
		m.fermerPinces(1200);
		m.tourner(100); m.avancer(20); m.tourner(-100); 
		vers_couleur(6);
		m.ouvrirPinces(1200);
		cpt_palet++;
		m.avancer(-10); m.fermerPinces(1200); 
	}
	public void troisPremiers() {
//
//		SampleProvider distanceProvider = this.c.getUltrason().getDistanceMode(); 
//		float[] sample = new float[distanceProvider.sampleSize()];
		 
		premierPalet();
		while(cpt_palet<3) {
			 ligneNoire();
			 m.avancer(-10);
			 perpPalet();
			 m.tourner(-m.getDirection());
			 vers_couleur(6);
			 m.ouvrirPinces(1200);
			 cpt_palet++;
			 m.avancer(-10); 
			 m.fermerPinces(1200);
		}
	}
	
	public void perpPalet() {
		SampleProvider distanceProvider = this.c.getUltrason().getDistanceMode(); 
		float[] sample = new float[distanceProvider.sampleSize()];
		m.tourner(-55);
		m.changerVitRot(36);
		int i = 0;
			
		ArrayList<Float> samples = new ArrayList<Float>();
		  m.tournerSync(110); while(m.isMoving()) {
			  distanceProvider.fetchSample(sample, 0); samples.add(sample[0] * 100); //Convertir en centimètres 
			  System.out.println(i+"- "+samples.get(i)); i++; }
			  
			  double minPalet=120;
		
			  for(int j=0; j<samples.size();j++) {
				  if(samples.get(j) < minPalet && samples.get(j) >= 32) { minPalet=samples.get(j); i=j; }
			  }

			  m.changerVitRot(90);
			  //if(i<=180) { m.tourner(i); }else m.tourner(i-360);
			  m.tourner(-110+i*110.000/samples.size()+10);
			  m.ouvrirPinces(1200);
			  m.avancer(minPalet+25);
			  m.fermerPinces(1200);
	}
	
	
	public void ligneCentrale() {
		while(cpt_palet<7) {
		 ligneNoire();
		 m.avancer(45);
		 perpPalet();
		 m.tourner(-m.getDirection());
		 vers_couleur(6);
		 m.ouvrirPinces(1200);
		 m.avancer(-10);
		 m.fermerPinces(1200);}}
	
	public void defense() {
		ligneNoire();
		vers_couleur(6);
		m.tourner(90);
		m.avancer(87);
		while(true) {
			m.avancer(-87*2);
			m.avancer(87*2);
		}
	}
}