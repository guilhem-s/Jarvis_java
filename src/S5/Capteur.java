package S5;

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

	public Capteur (){
		ultrason = new EV3UltrasonicSensor(SensorPort.S4);
		touche = new EV3TouchSensor(SensorPort.S3);
		couleur = new EV3ColorSensor(SensorPort.S2);
		
	}
	public EV3TouchSensor getTouche() {
		return touche;
	}
	public EV3UltrasonicSensor getUltrason() {
		return ultrason;
	}
	public EV3ColorSensor getCouleur() {
		return couleur;
	}

	public float echantillon() {
		 SampleProvider sp = ultrason.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		return sample[0];
	}

	public float[] getSample(int seuil){
		 SampleProvider sp = ultrason.getDistanceMode();
		int distanceValue = 0;
		float []samples = new float [seuil];
		for(int i = 0; i < seuil; i++){
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distanceValue = (int)sample[0];
			samples[i] = distanceValue;
			Delay.msDelay(500);
		}
		return samples;  
	}	
}