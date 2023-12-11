package S5;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;


/**
* La classe Capteur
*/ 
public class Capteur {
	
	private EV3UltrasonicSensor ultrason;
	private EV3TouchSensor touche;
	private EV3ColorSensor couleur;

	/** 
	 *
	 * C'est un constructeur. 
	 *
	 */
	public Capteur (){
		ultrason = new EV3UltrasonicSensor(SensorPort.S4);
		touche = new EV3TouchSensor(SensorPort.S3);
		couleur = new EV3ColorSensor(SensorPort.S2);	
	}
	/** 
	 *
	 * renvoie le capteur de toucher
	 *
	 * @return le capteur de toucher
	 */
	public EV3TouchSensor getTouche() {
		return touche;
	}
	/** 
	 *
	 * Renvoie le capteur ultrason
	 *
	 * @return le capteur ultrason
	 */
	public EV3UltrasonicSensor getUltrason() {
		return ultrason;
	}
	/** 
	 *
	 * Renvoie le capteur de couleur
	 *
	 * @return le capteur de couleur
	 */
	public EV3ColorSensor getCouleur() {
		return couleur;
	}
	
	/** 
	 *
	 * mesure la distance entre le capteur et le prochain obstacle
	 *
	 * @return float
	 */
	public float echantillon() {
		 SampleProvider sp = ultrason.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		return sample[0];
	}	
}