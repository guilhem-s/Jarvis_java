package S5;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Capteur {

	private EV3UltrasonicSensor ultrason;
	private EV3TouchSensor touche;
	private EV3ColorSensor couleur;
	Moteur m;

	public Capteur (){
		ultrason = new EV3UltrasonicSensor(SensorPort.S4);
		touche = new EV3TouchSensor(SensorPort.S3);
		couleur = new EV3ColorSensor(SensorPort.S2);

	}
	public int echantillon() {
		final SampleProvider sp = ultrason.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		float result = sample[0]*1000;
		return  (int) result;
	}


	public float[] getSample(int seuil){
		final SampleProvider sp = ultrason.getDistanceMode();
		float[] distanceValue = new float[seuil];
		for(int i = 0; i < seuil; i++){
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distanceValue[i] = sample[0];
			System.out.println("Valeur : " + i + "Distance : " + sample[0]);
			Delay.msDelay(500);
		}
		return distanceValue;  // A changer
	}

	public void vers_ligne_arrivee() {
		if(! couleur.isFloodlightOn())  // teste si la lampe est allumée
			couleur.setFloodlight(true);
		while (true) {
			m.avancerSync(100);
			int colorID = couleur.getColorID();
			if (colorID == 6) break;
			try {
				Thread.sleep(250); // Attendre 0,25 seconde entre les mesures
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Button.ENTER.isDown())break;
		}
		m.stop();
		couleur.close();
	}

}
