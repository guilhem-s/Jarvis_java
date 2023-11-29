package S5;

import java.util.ArrayList;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Capteur {

	private EV3UltrasonicSensor ultrason;
	private EV3TouchSensor toucher;
	private EV3ColorSensor couleur;

	public Capteur (){
		ultrason = new EV3UltrasonicSensor(SensorPort.S4);
		toucher = new EV3TouchSensor(SensorPort.S3);
		couleur = new EV3ColorSensor(SensorPort.S2);
	}
	
	public EV3TouchSensor getTouche() { return toucher;	}
	public EV3UltrasonicSensor getUltrason() { return ultrason;	}
	public EV3ColorSensor getCouleur() { return couleur; }

	public int[] getSample(int seuil, int delay){
		final SampleProvider sp = ultrason.getDistanceMode();
		int[] distanceValue = new int[seuil];
		for(int i = 0; i < seuil; i++){
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			sample[0] = sample[0]>300?300:sample[0];
			System.out.println((int) (sample[0]*100));
			distanceValue[i] = (int) (sample[0]*100);
			Delay.msDelay(delay);
		}
		return distanceValue;  // A changer
	}

	public ArrayList<Integer> echantillon_en_tournant(Moteur m, int angle_de_balayage) {
		int i = 0;
		final SampleProvider sp = ultrason.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		ArrayList<Integer> distanceValue = new ArrayList<Integer>();
		m.tournerSync(angle_de_balayage);
		while(m.isMoving()) {
			sp.fetchSample(sample, 0); 
			int echantillon = sample[0]>2.5?250:(int) (sample[0]*100); // Au delà de 250, les mesures n'ont pas d'utilité pour nous
			distanceValue.add(echantillon); // echantillon est entier et converti en centimètres 
			System.out.println(i+"- "+distanceValue.get(i));
			i++;			
		}
		return distanceValue;
	}
}
