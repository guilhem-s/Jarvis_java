package ev3dev.sensors.ev3;

import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class USSensorDemo {

	private EV3UltrasonicSensor ultraSensor;

	public USSensorDemo(SensorPort port){
		ultraSensor = new EV3UltrasonicSensor(port);
	}

	public float[] getSample(int seuil){
		final SampleProvider sp = ultraSensor.getDistance();
		int distanceValue = 0;
		for(int i = 0; i < seuil; i++){
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distanceValue = (int)sample[0];
			Delay.msDelay(500);
		}
		return sample;
	}
	

	public static void main(String[] args) {

		final SampleProvider sp = us1.getDistanceMode();
		int distanceValue = 0;

        final int iteration_threshold = 100;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int)sample[0];

			System.out.println("Iteration: " + i + ", Distance: " + distanceValue);

			Delay.msDelay(500);
        }

	}

}
