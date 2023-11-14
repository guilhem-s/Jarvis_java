package S5;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Capteur {

	private EV3UltrasonicSensor ultrason;
	private EV3TouchSensor toucher;
	private EV3ColorSensor capcouleur;
	Moteur m;

	public Capteur (){
		ultrason = new EV3UltrasonicSensor(SensorPort.S4);
		toucher = new EV3TouchSensor(SensorPort.S3);
		capcouleur = new EV3ColorSensor(SensorPort.S2);

	}
	public int echantillon() {
		final SampleProvider sp = ultrason.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		float result = sample[0]*100;
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

	
	public EV3TouchSensor getTouche() {
		// TODO Auto-generated method stub
		return toucher;
	}
	public EV3UltrasonicSensor getUltrason() {
		return ultrason;
	}
	public EV3ColorSensor getCapcouleur() {
		return capcouleur;
	}

}
